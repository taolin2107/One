package app.taolin.one.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.taolin.one.R;
import app.taolin.one.adapter.MoreHomeListAdapter;
import app.taolin.one.api.Api;
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.dao.DaoSession;
import app.taolin.one.dao.Home;
import app.taolin.one.dao.HomeDao;
import app.taolin.one.databinding.MoreHomeListLayoutBinding;
import app.taolin.one.models.MoreListModel;
import app.taolin.one.models.OldHome;
import app.taolin.one.utils.Constants;
import app.taolin.one.adapter.EventHandlers;
import app.taolin.one.utils.GsonRequest;
import app.taolin.one.utils.Utils;
import app.taolin.one.utils.VolleySingleton;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 18, 2016.
 * @description
 */

public class MoreHomeListActivity extends Activity {

    private String mContentDate;
    private HomeDao mHomeDao;
    private MoreHomeListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoreHomeListLayoutBinding layoutBinding = DataBindingUtil.setContentView(this, R.layout.more_home_list_layout);
        layoutBinding.setHandlers(new EventHandlers());
        layoutBinding.list.setHasFixedSize(true);
        layoutBinding.list.setLayoutManager(new GridLayoutManager(this, 2));

        List<MoreListModel> dataList = new ArrayList<>();
        mListAdapter = new MoreHomeListAdapter(dataList);
        layoutBinding.list.setAdapter(mListAdapter);

        mListAdapter.setOnItemClickListener(new MoreHomeListAdapter.ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MoreHomeListActivity.this, HomeActivity.class);
                final String contentId = mListAdapter.getItem(position).id;
                intent.putExtra(Constants.PARAMS_ID, contentId);
                startActivity(intent);
            }
        });

        mContentDate = getIntent().getStringExtra(Constants.PARAMS_DATE);
        layoutBinding.setDate(mContentDate);

        initDatabase();

        if (!getDataFromDatabase(false)) {
            getDataFromServer();
        }
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        mHomeDao = daoSession.getHomeDao();
    }

    private boolean getDataFromDatabase(boolean isOffline) {
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(mContentDate.substring(0, 4));
        int month = Integer.parseInt(mContentDate.substring(5)) - 1;

        int days;
        if (year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH)) {
            days = calendar.get(Calendar.DATE);
        } else {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            days = calendar.getActualMaximum(Calendar.DATE);
        }

        List<Home> homeList = mHomeDao.queryBuilder().where(HomeDao.Properties.MakeTime
                .like(mContentDate + "%")).orderDesc(HomeDao.Properties.MakeTime).list();
        int size = homeList.size();

        if (isOffline    //没有网络时从数据库加载
                || size == days
                || (size == (days - 1)   //修正老文章日期问题
                    && days > 1
                    && homeList.get(size - 1).getMakeTime().endsWith("02"))) {
            MoreListModel listModel;
            for (Home home : homeList) {
                listModel = new MoreListModel(home.getId(), home.getTitle(), home.getContent(),
                        home.getMakeTime(), home.getImageUrl());
                mListAdapter.addItem(listModel);
            }
            mListAdapter.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    private void getDataFromServer() {
        final String BaseMoreUrl = Utils.encryto(Utils.reverse(Api.URL_MORE_BASE), -9);
        GsonRequest articleReq = new GsonRequest<>(BaseMoreUrl + Api.URL_MORE_HOME_LIST + mContentDate, OldHome.class, null,
                new Response.Listener<OldHome>() {
                    @Override
                    public void onResponse(OldHome response) {
                        if ("0".equals(response.res)) {
                            MoreListModel listModel;
                            for (OldHome.Data h : response.data) {
                                Home home = new Home();
                                home.setId(h.getId());
                                home.setTitle(h.getTitle());
                                home.setImageUrl(h.getImageUrl());
                                home.setAuthor(h.getAuthor());
                                home.setContent(h.getContent());
                                home.setMakeTime(h.getMakeTime());
                                home.setWebLink(h.getWebLink());
                                home.setIsloaded(true);
                                try {
                                    mHomeDao.insert(home);
                                } catch (Exception e) {
                                    // duplicated primary key, skip the error
                                }
                                listModel = new MoreListModel(home.getId(), home.getTitle(),
                                        home.getContent(),home.getMakeTime(), home.getImageUrl());
                                mListAdapter.addItem(listModel);
                            }
                            mListAdapter.notifyDataSetChanged();
                        } else {
                            getDataFromDatabase(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Taolin", error.getLocalizedMessage() + "");
                        getDataFromDatabase(true);
                    }
                });
        VolleySingleton.getInstance().addToRequestQueue(articleReq);
    }
}
