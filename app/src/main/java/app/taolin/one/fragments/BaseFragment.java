package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.taolin.one.R;
import app.taolin.one.adapter.ViewPagerAdapter;
import app.taolin.one.widgets.ExtViewPager;

/**
 * @author taolin
 * @version v1.0
 * @date May 30, 2016.
 * @description
 */

public class BaseFragment extends Fragment {

    private ExtViewPager mViewPager;
    int mAdapterType = ViewPagerAdapter.TYPE_HOME;
    private ViewPagerAdapter mViewPagerAdapter;

    private View mRefreshLayout;
    private TextView mRefreshText;
    private ProgressBar mRefreshPb;
    private boolean mIsRefreshing;

    private int mPreviousX = -1;
    private int mCurrentX = -1;
    private int mTranslationX;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewpager, container, false);
        mViewPager = (ExtViewPager) root.findViewById(R.id.viewpager);
        mRefreshLayout = root.findViewById(R.id.refresh_layout);
        mRefreshText = (TextView) root.findViewById(R.id.tv_refresh);
        mRefreshPb = (ProgressBar) root.findViewById(R.id.pb_refresh);
        mTranslationX = getActivity().getResources().getDimensionPixelSize(R.dimen.refresh_layout_translation);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mAdapterType);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnTouchListener(mTouchListener);
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mViewPager.getCurrentItem() == 0) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (mIsRefreshing) {
                            return false;
                        }
                        mCurrentX = (int) event.getRawX();
                        if (mPreviousX >= 0) {
                            int moveX = mCurrentX - mPreviousX;
                            int transX = (int) mRefreshLayout.getTranslationX();
                            transX += moveX;
                            if (transX < mTranslationX) {
                                transX = mTranslationX;
                            } else if (transX > 0) {
                                transX = 0;
                            }
                            if (transX > mTranslationX / 4) {
                                onRefreshPrepare();
                            }
                            mRefreshLayout.setTranslationX(transX);
                            mViewPager.setTranslationX(-mTranslationX + transX);
                        }
                        mPreviousX = mCurrentX;
                        break;

                    case MotionEvent.ACTION_UP:
                        mPreviousX = -1;
                        mCurrentX = -1;
                        if (mRefreshLayout.getTranslationX() > mTranslationX / 4) {
                            onRefreshStart();
                        } else {
                            onRefreshDone();
                        }
                        break;
                }
            }
            return false;
        }
    };

    public void setToFirstPage() {
        mViewPager.setCurrentItem(0);
    }

    private void onRefreshPrepare() {
        mRefreshText.setText(R.string.refresh_text_start);
    }

    private void onRefreshStart() {
        if (!mIsRefreshing) {
            mViewPager.setEnabled(false);
            mRefreshText.setVisibility(View.GONE);
            mRefreshPb.setVisibility(View.VISIBLE);
            mViewPagerAdapter.notifyDataSetChanged();
            mIsRefreshing = true;
        }
    }

    public void onRefreshDone() {
        mViewPager.setEnabled(true);
        mRefreshLayout.setTranslationX(mTranslationX);
        mViewPager.setTranslationX(0);
        mRefreshText.setText(R.string.refresh_text_prepare);
        mRefreshText.setVisibility(View.VISIBLE);
        mRefreshPb.setVisibility(View.GONE);
        mIsRefreshing = false;
    }
}
