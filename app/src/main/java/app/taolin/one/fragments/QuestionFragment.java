package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import app.taolin.one.adapter.ViewPagerAdapter;

/**
 * @author taolin
 * @version v1.0
 * @date May 30, 2016.
 * @description
 */

public class QuestionFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapterType = ViewPagerAdapter.TYPE_QUESTION;
    }
}
