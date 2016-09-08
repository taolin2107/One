package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Calendar;

import app.taolin.one.R;
import app.taolin.one.databinding.LayoutArticleBinding;
import app.taolin.one.utils.Api;
import app.taolin.one.dao.Article;
import app.taolin.one.dao.ArticleDao;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.models.OldArticle;
import app.taolin.one.utils.GsonRequest;
import app.taolin.one.utils.VolleySingleton;
import app.taolin.one.widgets.ScrollViewExt;

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
    public void loadDate(final String date) {
        final ArticleDao articleDao = getDaoSession().getArticleDao();
        Article article = articleDao.queryBuilder().where(ArticleDao.Properties.MakeTime.eq(date)).unique();
        if (article != null) {
            if (article.getIsloaded()) {
                mViewBinding.setArticle(article);
            } else {
                GsonRequest articleItemReq = new GsonRequest<>(Api.URL_ARTICLE + article.getId(), OldArticle.ArticleItem.class, null,
                        new Response.Listener<OldArticle.ArticleItem>() {
                            @Override
                            public void onResponse(OldArticle.ArticleItem response) {
                                if ("0".equals(response.res)) {
                                    Article article = new Article();
                                    article.setId(response.getId());
                                    article.setTitle(response.getTitle());
                                    article.setSubTitle(response.getSubTitle());
                                    article.setAuthor(response.getAuthor());
                                    article.setAuthorDesc(response.getAuthorDesc());
                                    article.setAuthorIntro(response.getAuthorIntro());
                                    article.setContent(response.getContent());
                                    article.setMakeTime(response.getMakeTime());
                                    article.setWebLink(response.getWebLink());
                                    article.setGuideWord(response.getGuideWord());
                                    article.setIsloaded(true);
                                    try {
                                        articleDao.insertOrReplace(article);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    mViewBinding.setArticle(article);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Taolin", error.getLocalizedMessage());
                            }
                        });
                VolleySingleton.getInstance().addToRequestQueue(articleItemReq);
            }
        }
        final Calendar preloadDate = getPreloadDate(date);
        if (preloadDate != null) {
            Api.requestArticleData(preloadDate, articleDao);
        }
    }
}
