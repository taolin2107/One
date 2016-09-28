package app.taolin.one.activity;

import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import app.taolin.one.R;
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.dao.Home;
import app.taolin.one.dao.HomeDao;
import app.taolin.one.databinding.LayoutHomeBinding;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.utils.Constants;
import app.taolin.one.adapter.EventHandlers;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 27, 2016
 * @description
 */
public class HomeActivity extends BaseActivity {

    private LayoutHomeBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = DataBindingUtil.setContentView(this, R.layout.layout_home);
        mViewBinding.setHastoolbar(true);
        mViewBinding.setHandlers(new EventHandlers());
        setListeners();
    }

    private void setListeners() {
        mViewBinding.title.setOnClickListener(new ViewClickListener() {
            @Override
            public void onSingleClick(View v) {}

            @Override
            public void onDoubleClick(View v) {
                mViewBinding.scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        setTitleBar(mViewBinding.titlebar);
        setScrollView(mViewBinding.scrollView);
    }

    public void loadData(final String id) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        final HomeDao homeDao = daoMaster.newSession().getHomeDao();
        Home home = homeDao.queryBuilder().where(HomeDao.Properties.Id.eq(id)).unique();
        if (home != null) {
            mViewBinding.setHome(home);
        }
    }
}
