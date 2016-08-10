package app.taolin.one.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ScrollView;

import com.umeng.analytics.MobclickAgent;

import app.taolin.one.App;
import app.taolin.one.R;
import app.taolin.one.utils.Constants;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.listener.OnDataLoadListener;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.widgets.ScrollViewExt.OnScrollChangeListener;

/**
 * Created by Taolin on 16/5/27.
 */

abstract class BaseContentFragment extends Fragment {

    private OnContentScrollListener mScrollListener;
    private OnDataLoadListener mDataLoadListener;
    private int mStartScrollY;
    private int mEffectiveDist;
    private Handler mHandler;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataLoadListener = (OnDataLoadListener) getActivity();
        mHandler = new Handler();
        initData();
        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.PARAMS_DATE);
        loadDate(date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    static Bundle getArguments(int index) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PARAMS_DATE, DateUtil.getDateString(index));
        return bundle;
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

    void loadDone(long startTime) {
        // 最低要刷新2秒钟
        long delay = 2000 - System.currentTimeMillis() + startTime;
        if (delay < 0) {
            delay = 0;
        }
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDataLoadListener != null) {
                    mDataLoadListener.onLoadDone();
                }
            }
        }, delay);
    }

    public abstract void loadDate(String date);
}
