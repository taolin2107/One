package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import app.taolin.one.utils.Constants;

/**
 * @author taolin
 * @version v1.0
 * @date May 30, 2016.
 * @description
 */

public class ArticleFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapterType = Constants.TYPE_ARTICLE;
    }
}
