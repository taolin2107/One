package app.taolin.one.activity;

import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import app.taolin.one.R;
import app.taolin.one.api.Api;
import app.taolin.one.dao.Article;
import app.taolin.one.dao.ArticleDao;
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.databinding.LayoutArticleBinding;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.models.OldArticle;
import app.taolin.one.utils.Constants;
import app.taolin.one.adapter.EventHandlers;
import app.taolin.one.utils.GsonRequest;
import app.taolin.one.utils.Utils;
import app.taolin.one.utils.VolleySingleton;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 27, 2016
 * @description
 */

public class ArticleActivity extends BaseActivity {

    private LayoutArticleBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = DataBindingUtil.setContentView(this, R.layout.layout_article);
        mViewBinding.setHastoolbar(true);
        mViewBinding.setHandlers(new EventHandlers());
        setListeners();
    }

    private void setListeners() {
        mViewBinding.title.setOnClickListener(new ViewClickListener() {
            @Override
            public void onSingleClick(View v) {}

            @Override
            public void onDoubleClick(View v) {
                mViewBinding.scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        setTitleBar(mViewBinding.titlebar);
        setScrollView(mViewBinding.scrollView);
    }

    public void loadData(final String id) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        final ArticleDao articleDao =  daoMaster.newSession().getArticleDao();
        Article article = articleDao.queryBuilder().where(ArticleDao.Properties.Id.eq(id)).unique();
        if (article != null && article.getIsloaded()) {
            mViewBinding.setArticle(article);
        } else {
            final String BaseMoreUrl = Utils.encryto(Utils.reverse(Api.URL_MORE_BASE), -9);
            GsonRequest request = new GsonRequest<>(BaseMoreUrl + Api.URL_MORE_ARTICLE + id, OldArticle.ArticleItem.class, null,
                    new Response.Listener<OldArticle.ArticleItem>() {
                        @Override
                        public void onResponse(OldArticle.ArticleItem response) {
                            if ("0".equals(response.res)) {
                                OldArticle.Data a = response.data;
                                Article article = new Article();
                                article.setId(a.getId());
                                article.setTitle(a.getTitle());
                                article.setSubTitle(a.getSubTitle());
                                article.setAuthor(a.getAuthor());
                                article.setAuthorDesc(a.getAuthorDesc());
                                article.setAuthorIntro(a.getAuthorIntro());
                                article.setContent(a.getContent());
                                article.setMakeTime(a.getMakeTime());
                                article.setWebLink(a.getWebLink());
                                article.setGuideWord(a.getGuideWord());
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
            VolleySingleton.getInstance().addToRequestQueue(request);
        }
    }
}
