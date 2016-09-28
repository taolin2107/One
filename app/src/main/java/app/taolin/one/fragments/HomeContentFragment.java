package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.taolin.one.databinding.LayoutHomeBinding;
import app.taolin.one.models.LatestHome;
import app.taolin.one.dao.Home;
import app.taolin.one.dao.HomeDao;
import app.taolin.one.api.OneServiceSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void loadDate(final String date, final int row, final int ms) {
        final HomeDao homeDao = getDaoSession().getHomeDao();
        Home home = homeDao.queryBuilder().where(HomeDao.Properties.MakeTime.eq(date)).unique();
        if (home != null) {
            mViewBinding.setHome(home);
            return;
        }
        //这个api的参数date和index都有效,下面把index固定,只变化date
        Call<LatestHome> getHome = OneServiceSingleton.getInstance().mOneService.getHome(date, 1);
        getHome.enqueue(new Callback<LatestHome>() {
            @Override
            public void onResponse(Call<LatestHome> call, Response<LatestHome> response) {
                LatestHome latestHome = response.body();
                if ("SUCCESS".equals(latestHome.result)) {
                    Home home = new Home();
                    home.setId(latestHome.getId());
                    home.setImageUrl(latestHome.getImageUrl());
                    home.setContent(latestHome.getContent());
                    home.setWebLink(latestHome.getWebLink());
                    home.setAuthor(latestHome.getAuthor());
                    home.setMakeTime(latestHome.getMakeTime());
                    home.setTitle(latestHome.getTitle());
                    home.setIsloaded(true);
                    try {
                        homeDao.insertOrReplace(home);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mViewBinding.setHome(home);
                }
            }

            @Override
            public void onFailure(Call<LatestHome> call, Throwable t) {
                Log.e("HomeContentFragment", t + "");
            }
        });
    }
}
