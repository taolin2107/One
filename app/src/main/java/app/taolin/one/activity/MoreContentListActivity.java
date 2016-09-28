package app.taolin.one.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.taolin.one.R;
import app.taolin.one.adapter.MoreContentListAdapter;
import app.taolin.one.api.Api;
import app.taolin.one.dao.Article;
import app.taolin.one.dao.ArticleDao;
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.dao.DaoSession;
import app.taolin.one.dao.Question;
import app.taolin.one.dao.QuestionDao;
import app.taolin.one.databinding.MoreContentListLayoutBinding;
import app.taolin.one.models.MoreListModel;
import app.taolin.one.models.OldArticle;
import app.taolin.one.models.OldQuestion;
import app.taolin.one.utils.Constants;
import app.taolin.one.adapter.EventHandlers;
import app.taolin.one.utils.GsonRequest;
import app.taolin.one.utils.Utils;
import app.taolin.one.utils.VolleySingleton;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 18, 2016.
 * @description
 */

public class MoreContentListActivity extends Activity {

    private int mContentType;
    private String mContentDate;
    private DaoSession mDaoSession;
    private MoreContentListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoreContentListLayoutBinding layoutBinding = DataBindingUtil.setContentView(this,
                R.layout.more_content_list_layout);
        layoutBinding.setHandlers(new EventHandlers());
        List<MoreListModel> dataList = new ArrayList<>();
        mListAdapter = new MoreContentListAdapter(dataList);
        layoutBinding.list.setAdapter(mListAdapter);

        layoutBinding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String contentId = mListAdapter.getItem(position).id;
                if (mContentType == Constants.TYPE_ARTICLE) {
                    Intent intent = new Intent(MoreContentListActivity.this, ArticleActivity.class);
                    intent.putExtra(Constants.PARAMS_ID, contentId);
                    startActivity(intent);
                } else if (mContentType == Constants.TYPE_QUESTION) {
                    Intent intent = new Intent(MoreContentListActivity.this, QuestionActivity.class);
                    intent.putExtra(Constants.PARAMS_ID, contentId);
                    startActivity(intent);
                }
            }
        });

        mContentType = getIntent().getIntExtra(Constants.PARAMS_TYPE, 0);
        mContentDate = getIntent().getStringExtra(Constants.PARAMS_DATE);
        layoutBinding.setDate(mContentDate);

        initDatabase();

        if (!getDataFromDatabase(false)) {
            getDataFromServer();
        }
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    private boolean getDataFromDatabase(boolean isOffline) {
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(mContentDate.substring(0, 4));
        int month = Integer.parseInt(mContentDate.substring(5)) - 1;

        int days;
        if (year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH)) {
            days = calendar.get(Calendar.DATE);
        } else {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            days = calendar.getActualMaximum(Calendar.DATE);
        }

        if (Constants.TYPE_ARTICLE == mContentType) {
            final ArticleDao articleDao = mDaoSession.getArticleDao();
            List<Article> articleList = articleDao.queryBuilder().where(ArticleDao.Properties.MakeTime
                    .like(mContentDate + "%")).orderDesc(ArticleDao.Properties.MakeTime).list();
            int size = articleList.size();

            if (isOffline    //没有网络时从数据库加载
                || size == days
                || (size == (days - 1)   //修正老文章日期问题
                    && days > 1
                    && articleList.get(size - 1).getMakeTime().endsWith("02"))) {
                MoreListModel listModel;
                for (Article article : articleList) {
                    listModel = new MoreListModel(article.getId(), article.getTitle(),
                            article.getAuthor(), article.getGuideWord());
                    mListAdapter.addItem(listModel);
                }
                mListAdapter.notifyDataSetChanged();
                return true;
            } else {
                return false;
            }
        } else if (Constants.TYPE_QUESTION == mContentType) {
            final QuestionDao questionDao = mDaoSession.getQuestionDao();
            List<Question> questionList = questionDao.queryBuilder().where(QuestionDao.Properties.MakeTime
                    .like(mContentDate + "%")).orderDesc(QuestionDao.Properties.MakeTime).list();
            int size = questionList.size();

            if (isOffline    //没有网络时从数据库加载
                    || size == days
                    || (size == (days - 1)  //修正老文章日期问题
                        && days > 1
                        && questionList.get(size - 1).getMakeTime().endsWith("02"))) {
                MoreListModel listModel;
                for (Question question : questionList) {
                    listModel = new MoreListModel(question.getId(), question.getQuestionTitle(), question.getAnswerTitle());
                    mListAdapter.addItem(listModel);
                }
                mListAdapter.notifyDataSetChanged();
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private void getDataFromServer() {
        final String BaseMoreUrl = Utils.encryto(Utils.reverse(Api.URL_MORE_BASE), -9);
        if (Constants.TYPE_ARTICLE == mContentType) {
            GsonRequest articleReq = new GsonRequest<>(BaseMoreUrl + Api.URL_MORE_ARTICLE_LIST + mContentDate, OldArticle.class, null,
                    new Response.Listener<OldArticle>() {
                        @Override
                        public void onResponse(OldArticle response) {
                            if ("0".equals(response.res)) {
                                final ArticleDao articleDao = mDaoSession.getArticleDao();
                                MoreListModel listModel;
                                for (OldArticle.Data a : response.data) {
                                    Article article = new Article();
                                    article.setId(a.getId());
                                    article.setTitle(a.getTitle());
                                    article.setAuthor(a.getAuthor());
                                    article.setMakeTime(a.getMakeTime());
                                    article.setGuideWord(a.getGuideWord());
                                    article.setIsloaded(false);
                                    try {
                                        articleDao.insert(article);
                                    } catch (Exception e) {
                                        // duplicated primary key, skip the error
                                    }
                                    listModel = new MoreListModel(article.getId(), article.getTitle(),
                                            article.getAuthor(), article.getGuideWord());
                                    mListAdapter.addItem(listModel);
                                }
                                mListAdapter.notifyDataSetChanged();
                            } else {
                                getDataFromDatabase(true);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Taolin", error.getLocalizedMessage() + "");
                            getDataFromDatabase(true);
                        }
                    });
            VolleySingleton.getInstance().addToRequestQueue(articleReq);
        } else if (Constants.TYPE_QUESTION == mContentType) {
            GsonRequest questionReq = new GsonRequest<>(BaseMoreUrl + Api.URL_MORE_QUESTION_LIST + mContentDate, OldQuestion.class, null,
                    new Response.Listener<OldQuestion>() {
                        @Override
                        public void onResponse(OldQuestion response) {
                            if ("0".equals(response.res)) {
                                final QuestionDao questionDao = mDaoSession.getQuestionDao();
                                MoreListModel listModel;
                                for (OldQuestion.Data q : response.data) {
                                    Question question = new Question();
                                    question.setId(q.getId());
                                    question.setQuestionTitle(q.getQuestionTitle());
                                    question.setAnswerTitle(q.getAnswerTitle());
                                    question.setGuideWord(q.getAnswerContent());
                                    question.setMakeTime(q.getMakeTime());
                                    question.setIsloaded(false);
                                    try {
                                        questionDao.insert(question);
                                    } catch (Exception e) {
                                        // duplicated primary key, skip the error
                                    }
                                    listModel = new MoreListModel(question.getId(), question.getQuestionTitle(),
                                            question.getGuideWord());
                                    mListAdapter.addItem(listModel);
                                }
                                mListAdapter.notifyDataSetChanged();
                            } else {
                                getDataFromDatabase(true);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Taolin", error.getLocalizedMessage() + "");
                            getDataFromDatabase(true);
                        }
                    });
            VolleySingleton.getInstance().addToRequestQueue(questionReq);
        }
    }
}
