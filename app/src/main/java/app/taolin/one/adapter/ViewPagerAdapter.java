package app.taolin.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.taolin.one.common.Constants;
import app.taolin.one.fragments.ArticleContentFragment;
import app.taolin.one.fragments.HomeContentFragment;
import app.taolin.one.fragments.QuestionContentFragment;

/**
 * Created by Taolin on 16/5/27.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final int TYPE_HOME = 1;
    public static final int TYPE_ARTICLE = 2;
    public static final int TYPE_QUESTION = 3;

    private int mAdapterType;

    public ViewPagerAdapter(FragmentManager fm, int adapterType) {
        super(fm);
        mAdapterType = adapterType;
    }

    public void setAdapterType(int type) {
        mAdapterType = type;
    }

    @Override
    public Fragment getItem(int position) {
        switch (mAdapterType) {
            case TYPE_HOME:
                return HomeContentFragment.getInstance(position);

            case TYPE_ARTICLE:
                return ArticleContentFragment.getInstance(position);

            case TYPE_QUESTION:
                return QuestionContentFragment.getInstance(position);
        }
        return HomeContentFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return Constants.MAX_PAGE_NUM;
    }
}
