package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.taolin.one.R;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.models.Article;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.utils.OneServiceSingleton;
import app.taolin.one.widgets.ScrollViewExt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void loadDate(final String date, final int row, final int ms) {
        Call<Article> getArticle = OneServiceSingleton.getInstance().mOneService.getArticle(date, row, ms);
        getArticle.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Article article = response.body();
                if ("SUCCESS".equals(article.result)) {
                    mDate.setText(DateUtil.getDisplayDate(article.contentEntity.strContMarketTime));
                    mTitle.setText(article.contentEntity.strContTitle);
                    mAuthor.setText(article.contentEntity.strContAuthor);
                    mContent.setText(Html.fromHtml(article.contentEntity.strContent));
                    mEditor.setText(article.contentEntity.strContAuthorIntroduce);
                    mAuthor2.setText(article.contentEntity.strContAuthor);
                    mAuthorIntro.setText(article.contentEntity.sAuth);
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("ArticleContentFragment", t + "");
            }
        });
    }
}
