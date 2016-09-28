package app.taolin.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.taolin.one.fragments.MoreContentFragment;
import app.taolin.one.utils.Constants;
import app.taolin.one.fragments.ArticleContentFragment;
import app.taolin.one.fragments.HomeContentFragment;
import app.taolin.one.fragments.QuestionContentFragment;

/**
 * Created by Taolin on 16/5/27.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int mAdapterType;

    public ViewPagerAdapter(FragmentManager fm, int adapterType) {
        super(fm);
        mAdapterType = adapterType;
    }

    public void setAdapterType(int type) {
        mAdapterType = type;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == Constants.MAX_PAGE_NUM - 1) {
            return MoreContentFragment.getInstance(mAdapterType);
        }
        switch (mAdapterType) {
            case Constants.TYPE_HOME:
                return HomeContentFragment.getInstance(position);

            case Constants.TYPE_ARTICLE:
                return ArticleContentFragment.getInstance(position);

            case Constants.TYPE_QUESTION:
                return QuestionContentFragment.getInstance(position);
        }
        return HomeContentFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return Constants.MAX_PAGE_NUM;
    }
}
