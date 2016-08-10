package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.taolin.one.R;
import app.taolin.one.adapter.ViewPagerAdapter;

/**
 * @author taolin
 * @version v1.0
 * @date May 30, 2016.
 * @description
 */

public class BaseFragment extends Fragment {

    private ViewPager mViewPager;
    int mAdapterType = ViewPagerAdapter.TYPE_HOME;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewpager, container, false);
        mViewPager = (ViewPager) root.findViewById(R.id.viewpager);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mAdapterType);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(viewPagerAdapter);
    }

    public void setToFirstPage() {
        mViewPager.setCurrentItem(0);
    }
}
