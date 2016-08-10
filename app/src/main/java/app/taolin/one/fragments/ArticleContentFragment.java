package app.taolin.one.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import app.taolin.one.App;
import app.taolin.one.R;
import app.taolin.one.utils.Api;
import app.taolin.one.utils.Constants;
import app.taolin.one.dao.Article;
import app.taolin.one.dao.ArticleDao;
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.dao.DaoSession;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.models.ArticleModel;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.utils.GsonRequest;
import app.taolin.one.utils.VolleySingleton;
import app.taolin.one.widgets.ScrollViewExt;

/**
 * Created by Taolin on 16/5/28.
 */

public class ArticleContentFragment extends BaseContentFragment {

    private TextView mDate;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mContent;
    private TextView mEditor;
    private TextView mAuthor2;
    private TextView mAuthorIntro;

    public static ArticleContentFragment getInstance(int index) {
        ArticleContentFragment fragment = new ArticleContentFragment();
        fragment.setArguments(getArguments(index));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_article, container, false);
        mDate = (TextView) root.findViewById(R.id.date);
        mTitle = (TextView) root.findViewById(R.id.title);
        mAuthor = (TextView) root.findViewById(R.id.author);
        mContent = (TextView) root.findViewById(R.id.content);
        mEditor = (TextView) root.findViewById(R.id.editor);
        mAuthor2 = (TextView) root.findViewById(R.id.author2);
        mAuthorIntro = (TextView) root.findViewById(R.id.author_intro);
        setDoubleClickListener(root);

        // for scroll down to hide toolbar, scroll up to show toolbar
        setOnContentScrollListener((OnContentScrollListener) getActivity());
        ScrollViewExt contentScroller = (ScrollViewExt) root.findViewById(R.id.scroll_view);
        contentScroller.setOnScrollChangeListener(mScrollChangeListener);
        return root;
    }

    @Override
    public void loadDate(final String date) {
        final long startTime = System.currentTimeMillis();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(App.getInstance(), Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        final ArticleDao articleDao = daoSession.getArticleDao();
        Article article = articleDao.queryBuilder().where(ArticleDao.Properties.Makettime.eq(date)).unique();
        if (article != null) {
            if (article.getIsloaded()) {
                mDate.setText(DateUtil.getDisplayDate(article.getMakettime()));
                mTitle.setText(article.getTitle());
                mAuthor.setText(article.getAuthor());
                mContent.setText(Html.fromHtml(article.getContent()));
                mEditor.setText(article.getAuthorintro());
                mAuthor2.setText(article.getAuthor());
                mAuthorIntro.setText(article.getAuthit());
                loadDone(startTime);
            } else {
                GsonRequest articleItemReq = new GsonRequest<>(Api.URL_ARTICLE + article.getId(), ArticleModel.ArticleItem.class, null,
                        new Response.Listener<ArticleModel.ArticleItem>() {
                            @Override
                            public void onResponse(ArticleModel.ArticleItem response) {
                                if ("0".equals(response.res)) {
                                    ArticleModel.Data a = response.data;
                                    Article article = new Article();
                                    article.setId(a.content_id);
                                    article.setTitle(a.hp_title);
                                    article.setSubtitle(a.sub_title);
                                    article.setAuthor(a.hp_author);
                                    article.setAuthit(a.auth_it);
                                    article.setAuthorintro(a.hp_author_introduce);
                                    article.setContent(a.hp_content);
                                    article.setMakettime(a.hp_makettime.substring(0, 10));
                                    article.setUpdatedate(a.last_update_date);
                                    article.setWeburl(a.web_url);
                                    article.setGuideword(a.guide_word);
                                    article.setAudio(a.audio);
                                    article.setPraisenum(a.praisenum);
                                    article.setSharenum(a.sharenum);
                                    article.setCommentnum(a.commentnum);
                                    article.setIsloaded(true);
                                    try {
                                        articleDao.insertOrReplace(article);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    mDate.setText(DateUtil.getDisplayDate(article.getMakettime()));
                                    mTitle.setText(article.getTitle());
                                    mAuthor.setText(article.getAuthor());
                                    mContent.setText(Html.fromHtml(article.getContent()));
                                    mEditor.setText(article.getAuthorintro());
                                    mAuthor2.setText(article.getAuthor());
                                    mAuthorIntro.setText(article.getAuthit());
                                }
                                loadDone(startTime);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Taolin", error.getLocalizedMessage());
                                loadDone(startTime);
                            }
                        });
                VolleySingleton.getInstance().addToRequestQueue(articleItemReq);
            }
        } else {
        }
    }
}
