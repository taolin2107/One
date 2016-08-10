package app.taolin.one.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Date;

import app.taolin.one.App;
import app.taolin.one.R;
import app.taolin.one.utils.Api;
import app.taolin.one.utils.Constants;
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.dao.DaoSession;
import app.taolin.one.dao.Home;
import app.taolin.one.dao.HomeDao;
import app.taolin.one.models.HomeModel;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.utils.GsonRequest;
import app.taolin.one.utils.VolleySingleton;

/**
 * Created by Taolin on 16/5/27.
 */

public class HomeContentFragment extends BaseContentFragment {

    private TextView mTitle;
    private NetworkImageView mPhoto;
    private TextView mAuthor;
    private TextView mDay;
    private TextView mMonthYear;
    private TextView mContent;

    public static HomeContentFragment getInstance(int index) {
        HomeContentFragment fragment = new HomeContentFragment();
        fragment.setArguments(getArguments(index));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_home, container, false);
        mTitle = (TextView) root.findViewById(R.id.title);
        mPhoto = (NetworkImageView) root.findViewById(R.id.photo);
        mAuthor = (TextView) root.findViewById(R.id.author);
        mDay = (TextView) root.findViewById(R.id.day);
        mMonthYear = (TextView) root.findViewById(R.id.month_year);
        mContent = (TextView) root.findViewById(R.id.content);
        setDoubleClickListener(root);
        return root;
    }

    @Override
    public void loadDate(final String date) {
        final long startTime = System.currentTimeMillis();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(App.getInstance(), Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        final HomeDao homeDao = daoSession.getHomeDao();
        Home home = homeDao.queryBuilder().where(HomeDao.Properties.Makettime.eq(date)).unique();
        if (home != null) {
            mTitle.setText(home.getTitle());
            mPhoto.setImageUrl(home.getImgurl(), VolleySingleton.getInstance().getImageLoader());
            mAuthor.setText(home.getAuthor().replace("&", "\n"));
            Date maketDate = DateUtil.getDate(home.getMakettime());
            mDay.setText(DateUtil.getDay(maketDate));
            mMonthYear.setText(DateUtil.getMonthYear(maketDate));
            mContent.setText(home.getContent().trim());
            loadDone(startTime);
        } else {
            GsonRequest homeReq = new GsonRequest<>(Api.URL_HOME_LIST + date.substring(0, 7), HomeModel.class, null,
                    new Response.Listener<HomeModel>() {
                        @Override
                        public void onResponse(HomeModel response) {
                            if ("0".equals(response.res)) {
                                for (HomeModel.Data h: response.data) {
                                    Home home = new Home();
                                    home.setId(h.hpcontent_id);
                                    home.setTitle(h.hp_title);
                                    home.setImgurl(h.hp_img_original_url);
                                    home.setAuthor(h.hp_author);
                                    home.setContent(h.hp_content);
                                    home.setMakettime(h.hp_makettime.substring(0, 10));
                                    home.setUpdatedate(h.last_update_date);
                                    home.setWeburl(h.web_url);
                                    home.setPraisenum(h.praisenum);
                                    home.setSharenum(h.sharenum);
                                    home.setCommentnum(h.commentnum);
                                    home.setIsloaded(true);
                                    try {
                                        homeDao.insert(home);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (h.hp_makettime.contains(date)) {
                                        mTitle.setText(home.getTitle());
                                        mPhoto.setImageUrl(home.getImgurl(), VolleySingleton.getInstance().getImageLoader());
                                        mAuthor.setText(home.getAuthor().replace("&", "\n"));
                                        Date maketDate = DateUtil.getDate(home.getMakettime());
                                        mDay.setText(DateUtil.getDay(maketDate));
                                        mMonthYear.setText(DateUtil.getMonthYear(maketDate));
                                        mContent.setText(home.getContent().trim());
                                    }
                                }
                            }
                            loadDone(startTime);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Taolin", error.getLocalizedMessage());
                            loadDone(startTime);
                        }
                    });
            VolleySingleton.getInstance().addToRequestQueue(homeReq);
        }
    }
}
