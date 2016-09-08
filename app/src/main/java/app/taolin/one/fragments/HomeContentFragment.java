package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import app.taolin.one.databinding.LayoutHomeBinding;
import app.taolin.one.utils.Api;
import app.taolin.one.dao.Home;
import app.taolin.one.dao.HomeDao;

/**
 * Created by Taolin on 16/5/27.
 */

public class HomeContentFragment extends BaseContentFragment {

    public static HomeContentFragment getInstance(int index) {
        HomeContentFragment fragment = new HomeContentFragment();
        fragment.setArguments(getArguments(index));
        return fragment;
    }

    private LayoutHomeBinding mViewBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewBinding = LayoutHomeBinding.inflate(inflater, container, false);
        setDoubleClickListener(mViewBinding.rootView);
        return mViewBinding.rootView;
    }

    @Override
    public void loadDate(final String date) {
        final HomeDao homeDao = getDaoSession().getHomeDao();
        Home home = homeDao.queryBuilder().where(HomeDao.Properties.MakeTime.eq(date)).unique();
        if (home != null) {
            mViewBinding.setHome(home);
        }
        final Calendar preloadDate = getPreloadDate(date);
        if (preloadDate != null) {
            Api.requestHomeData(preloadDate, homeDao);
        }
    }
}
