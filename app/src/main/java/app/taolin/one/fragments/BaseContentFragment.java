package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ScrollView;

import app.taolin.one.R;
import app.taolin.one.common.Constants;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.widgets.ScrollViewExt.OnScrollChangeListener;

/**
 * Created by Taolin on 16/5/27.
 */

public abstract class BaseContentFragment extends Fragment {

    private OnContentScrollListener mScrollListener;
    private int mStartScrollY;
    private int mEffectiveDist;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.PARAMS_DATE);
        int index = bundle.getInt(Constants.PARAMS_INDEX);
        int ms = bundle.getInt(Constants.PARAMS_MS);
        loadDate(date, index, ms);
        initData();
    }

    protected static Bundle getArguments(int index) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PARAMS_DATE, DateUtil.getDateString());
        bundle.putInt(Constants.PARAMS_INDEX, index + 1);   //index从1开始
        bundle.putInt(Constants.PARAMS_MS, 1);
        return bundle;
    }

    private void initData() {
        mEffectiveDist = getContext().getResources().getDimensionPixelSize(R.dimen.effective_scroll_distance);
        mStartScrollY = -1;
    }

    public void setDoubleClickListener(View root) {
        final ScrollView contentScroll = (ScrollView) root.findViewById(R.id.scroll_view);
        root.findViewById(R.id.double_click_area).setOnClickListener(new ViewClickListener() {
            @Override
            public void onSingleClick(View v) {
            }

            @Override
            public void onDoubleClick(View v) {
                contentScroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    protected void setOnContentScrollListener(OnContentScrollListener listener) {
        mScrollListener = listener;
    }

    protected OnScrollChangeListener mScrollChangeListener = new OnScrollChangeListener() {
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

    public abstract void loadDate(String date, int row, int ms);
}
