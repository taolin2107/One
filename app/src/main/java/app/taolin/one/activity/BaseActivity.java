package app.taolin.one.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import app.taolin.one.R;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.utils.Constants;
import app.taolin.one.widgets.ScrollViewExt;
import app.taolin.one.widgets.ScrollViewExt.OnScrollChangeListener;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 28, 2016
 * @description
 */

public abstract class BaseActivity extends Activity implements OnContentScrollListener {

    private static final int ANIM_DURATION = 200;
    private OnContentScrollListener mScrollListener;
    private int mStartScrollY;
    private int mEffectiveDist;

    private View mTitleBar;
    private int mTitleBarHeight;
    private boolean mIsTitleBarHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String id = getIntent().getStringExtra(Constants.PARAMS_ID);
        loadData(id);
    }

    private void initData() {
        mEffectiveDist = getResources().getDimensionPixelSize(R.dimen.effective_scroll_distance);
        mTitleBarHeight = getResources().getDimensionPixelSize(R.dimen.titlebar_height);
        mStartScrollY = -1;
        mScrollListener = this;
    }

    /**
     * must be called by child class
     */
    void setTitleBar(View titleBar) {
        mTitleBar = titleBar;
    }

    /**
     * must be called by child class
     */
    void setScrollView(ScrollViewExt scrollView) {
        scrollView.setOnScrollChangeListener(mScrollChangeListener);
    }

    abstract void loadData(String id);

    @Override
    public void scrollUp(View v) {
        if (mIsTitleBarHide) {
            mTitleBar.animate().cancel();
            mTitleBar.animate().translationY(0).setDuration(ANIM_DURATION);
            mIsTitleBarHide = false;
        }
    }

    @Override
    public void scrollDown(View v) {
        if (!mIsTitleBarHide) {
            mTitleBar.animate().cancel();
            mTitleBar.animate().translationY(-mTitleBarHeight).setDuration(ANIM_DURATION);
            mIsTitleBarHide = true;
        }
    }

    private OnScrollChangeListener mScrollChangeListener = new OnScrollChangeListener() {
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
}
