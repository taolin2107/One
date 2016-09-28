package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.taolin.one.R;
import app.taolin.one.databinding.LayoutArticleBinding;
import app.taolin.one.models.LatestArticle;
import app.taolin.one.dao.Article;
import app.taolin.one.dao.ArticleDao;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.api.OneServiceSingleton;
import app.taolin.one.widgets.ScrollViewExt;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Taolin on 16/5/28.
 */

public class ArticleContentFragment extends BaseContentFragment {

    public static ArticleContentFragment getInstance(int index) {
        ArticleContentFragment fragment = new ArticleContentFragment();
        fragment.setArguments(getArguments(index));
        return fragment;
    }

    private LayoutArticleBinding mViewBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewBinding = LayoutArticleBinding.inflate(inflater, container, false);
        setDoubleClickListener(mViewBinding.rootView);

        // for scroll down to hide toolbar, scroll up to show toolbar
        setOnContentScrollListener((OnContentScrollListener) getActivity());
        ScrollViewExt contentScroller = (ScrollViewExt) mViewBinding.rootView.findViewById(R.id.scroll_view);
        contentScroller.setOnScrollChangeListener(mScrollChangeListener);
        return mViewBinding.rootView;
    }

    @Override
    public void loadDate(final String date, final int row, final int ms) {
        final ArticleDao articleDao = getDaoSession().getArticleDao();
        Article article = articleDao.queryBuilder().where(ArticleDao.Properties.MakeTime.eq(date)).unique();
        if (article != null && article.getIsloaded()) {
            mViewBinding.setArticle(article);
            return;
        }
        //这个api只和row有关,date没用
        Call<LatestArticle> getArticle = OneServiceSingleton.getInstance().mOneService.getArticle(date, row, ms);
        getArticle.enqueue(new Callback<LatestArticle>() {
            @Override
            public void onResponse(Call<LatestArticle> call, retrofit2.Response<LatestArticle> response) {
                LatestArticle latestArticle = response.body();
                if ("SUCCESS".equals(latestArticle.result)) {
                    Article article = new Article();
                    article.setId(latestArticle.getId());
                    article.setTitle(latestArticle.getTitle());
                    article.setSubTitle(latestArticle.getSubTitle());
                    article.setAuthor(latestArticle.getAuthor());
                    article.setAuthorDesc(latestArticle.getAuthorDesc());
                    article.setAuthorIntro(latestArticle.getAuthorIntro());
                    article.setContent(latestArticle.getContent());
                    article.setMakeTime(latestArticle.getMakeTime());
                    article.setWebLink(latestArticle.getWebLink());
                    article.setGuideWord(latestArticle.getGuideWord());
                    article.setIsloaded(true);
                    try {
                        articleDao.insertOrReplace(article);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mViewBinding.setArticle(article);
                }
            }

            @Override
            public void onFailure(Call<LatestArticle> call, Throwable t) {
                Log.e("ArticleContentFragment", t + "");
            }
        });
    }
}
