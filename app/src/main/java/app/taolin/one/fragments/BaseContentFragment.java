package app.taolin.one.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ScrollView;

import com.umeng.analytics.MobclickAgent;

import java.util.Calendar;

import app.taolin.one.App;
import app.taolin.one.R;
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.dao.DaoSession;
import app.taolin.one.utils.Constants;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.widgets.ScrollViewExt.OnScrollChangeListener;

/**
 * Created by Taolin on 16/5/27.
 */

abstract class BaseContentFragment extends Fragment {

    private OnContentScrollListener mScrollListener;
    private int mStartScrollY;
    private int mEffectiveDist;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.PARAMS_DATE);
        loadDate(date);
    }

    static Bundle getArguments(int index) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PARAMS_DATE, DateUtil.getDateString(index));
        return bundle;
    }

    DaoSession getDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(App.getInstance(), Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        return daoMaster.newSession();
    }

    /**
     * indicate month will changed soon
     * @param date format must be yyyy-MM-dd
     */
    String getPreloadMonth(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateUtil.getDate(date).getTime());
        int day = calendar.get(Calendar.DATE);
        if (day < 4) {
            calendar.add(Calendar.MONTH, -1);
            return DateUtil.getRequestDate(calendar.getTime());
        } else if (day > 25) {
            calendar.add(Calendar.MONTH, 1);
            return DateUtil.getRequestDate(calendar.getTime());
        }
        return null;
    }

    private void initData() {
        mEffectiveDist = getContext().getResources().getDimensionPixelSize(R.dimen.effective_scroll_distance);
        mStartScrollY = -1;
    }

    void setDoubleClickListener(View root) {
        final ScrollView contentScroll = (ScrollView) root.findViewById(R.id.scroll_view);
        root.findViewById(R.id.double_click_area).setOnClickListener(new ViewClickListener() {
            @Override
            public void onSingleClick(View v) {
            }

            @Override
            public void onDoubleClick(View v) {
                contentScroll.fullScroll(ScrollView.FOCUS_UP);
                MobclickAgent.onEvent(App.getInstance(), "scroll_to_top");
            }
        });
    }

    void setOnContentScrollListener(OnContentScrollListener listener) {
        mScrollListener = listener;
    }

    OnScrollChangeListener mScrollChangeListener = new OnScrollChangeListener() {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (mScrollListener != null) {
                if (mStartScrollY == -1) {
                    mStartScrollY = scrollY;
                }
                if (scrollY - mStartScrollY > mEffectiveDist) {
                    mStartScrollY = scrollY;
                    mScrollListener.scrollDown(v);
                }
                if (mStartScrollY - scrollY > mEffectiveDist) {
                    mStartScrollY = scrollY;
                    mScrollListener.scrollUp(v);
                }
            }
        }
    };

    public abstract void loadDate(String date);
}
