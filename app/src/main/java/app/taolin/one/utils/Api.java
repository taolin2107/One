package app.taolin.one.utils;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Calendar;

import app.taolin.one.dao.Article;
import app.taolin.one.dao.ArticleDao;
import app.taolin.one.dao.Home;
import app.taolin.one.dao.HomeDao;
import app.taolin.one.dao.Question;
import app.taolin.one.dao.QuestionDao;
import app.taolin.one.models.ArticleModel;
import app.taolin.one.models.HomeModel;
import app.taolin.one.models.QuestionModel;

/**
 * Created by Taolin on 16/5/26.
 */

public class Api {

    private static final String URL_BASE = "http://v3.wufazhuce.com:8000/api";

    private static final String URL_HOME_LIST = URL_BASE + "/hp/bymonth/";

    private static final String URL_ARTICLE_LIST = URL_BASE + "/essay/bymonth/";

    public static final String URL_ARTICLE = URL_BASE + "/essay/";

    private static final String URL_QUESTION_LIST = URL_BASE + "/question/bymonth/";

    public static final String URL_QUESTION = URL_BASE + "/question/";

    private static String getRequestDate(final Calendar calendar) {
        Calendar todayCalendar = Calendar.getInstance();
        int todayMonth = todayCalendar.get(Calendar.MONTH);
        int targetMonth = calendar.get(Calendar.MONTH);
        if (targetMonth > todayMonth) {
            return null;
        } else if (targetMonth == todayMonth) {
            return DateUtil.getDateString(0);
        } else {
            return DateUtil.getRequestDateString(calendar.getTime());
        }
    }

    public static void requestHomeData(final Calendar calendar, final HomeDao homeDao) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String date = getRequestDate(calendar);
                if (null == date || null != homeDao.queryBuilder().where(HomeDao.Properties.Makettime.eq(date)).unique()) {
                    return;
                }
                GsonRequest homeReq = new GsonRequest<>(Api.URL_HOME_LIST + date, HomeModel.class, null,
                        new Response.Listener<HomeModel>() {
                            @Override
                            public void onResponse(HomeModel response) {
                                if ("0".equals(response.res)) {
                                    for (HomeModel.Data h: response.data) {
                                        Home home = new Home();
                                        home.setId(h.hpcontent_id);
                                        home.setTitle(h.hp_title);
                                        home.setImgurl(h.hp_img_original_url);
                                        home.setAuthor(h.hp_author);
                                        home.setContent(h.hp_content);
                                        home.setMakettime(h.hp_makettime.substring(0, 10));
                                        home.setUpdatedate(h.last_update_date);
                                        home.setWeburl(h.web_url);
                                        home.setPraisenum(h.praisenum);
                                        home.setSharenum(h.sharenum);
                                        home.setCommentnum(h.commentnum);
                                        home.setIsloaded(true);
                                        try {
                                            homeDao.insert(home);
                                        } catch (Exception e) {
                                            // duplicated primary key, skip the error
                                        }
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Taolin", error.getLocalizedMessage());
                            }
                        });
                VolleySingleton.getInstance().addToRequestQueue(homeReq);
            }
        }).start();
    }

    public static void requestArticleData(final Calendar calendar, final ArticleDao articleDao) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String date = getRequestDate(calendar);
                if (null == date || null != articleDao.queryBuilder().where(ArticleDao.Properties.Makettime.eq(date)).unique()) {
                    return;
                }
                GsonRequest articleReq = new GsonRequest<>(Api.URL_ARTICLE_LIST + date, ArticleModel.class, null,
                        new Response.Listener<ArticleModel>() {
                            @Override
                            public void onResponse(ArticleModel response) {
                                if ("0".equals(response.res)) {
                                    for (ArticleModel.Data a : response.data) {
                                        Article article = new Article();
                                        article.setId(a.content_id);
                                        article.setTitle(a.hp_title);
                                        article.setMakettime(a.hp_makettime.substring(0, 10));
                                        article.setGuideword(a.guide_word);
                                        article.setIsloaded(false);
                                        try {
                                            articleDao.insert(article);
                                        } catch (Exception e) {
                                            // duplicated primary key, skip the error
                                        }
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Taolin", error.getLocalizedMessage());
                            }
                        });
                VolleySingleton.getInstance().addToRequestQueue(articleReq);
            }
        }).start();
    }

    public static void requestQuestionData(final Calendar calendar, final QuestionDao questionDao) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String date = getRequestDate(calendar);
                if (null == date || null != questionDao.queryBuilder().where(QuestionDao.Properties.Makettime.eq(date)).unique()) {
                    return;
                }
                GsonRequest questionReq = new GsonRequest<>(Api.URL_QUESTION_LIST + date, QuestionModel.class, null,
                        new Response.Listener<QuestionModel>() {
                            @Override
                            public void onResponse(QuestionModel response) {
                                if ("0".equals(response.res)) {
                                    for (QuestionModel.Data q : response.data) {
                                        Question question = new Question();
                                        question.setId(q.question_id);
                                        question.setQuestiontitle(q.question_title);
                                        question.setAnswertitle(q.answer_title);
                                        question.setAnswercontent(q.answer_content);
                                        question.setMakettime(q.question_makettime.substring(0, 10));
                                        question.setIsloaded(false);
                                        try {
                                            questionDao.insert(question);
                                        } catch (Exception e) {
                                            // duplicated primary key, skip the error
                                        }
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Taolin", error.getLocalizedMessage());
                            }
                        });
                VolleySingleton.getInstance().addToRequestQueue(questionReq);
            }
        }).start();
    }
}
