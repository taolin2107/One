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

import app.taolin.one.dao.Question;
import app.taolin.one.dao.QuestionDao;
import app.taolin.one.databinding.LayoutQuestionBinding;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.models.OldQuestion;
import app.taolin.one.utils.Api;
import app.taolin.one.utils.GsonRequest;
import app.taolin.one.utils.VolleySingleton;

/**
 * Created by Taolin on 16/5/28.
 */

public class QuestionContentFragment extends BaseContentFragment {

    public static QuestionContentFragment getInstance(int index) {
        QuestionContentFragment fragment = new QuestionContentFragment();
        fragment.setArguments(getArguments(index));
        return fragment;
    }

    private LayoutQuestionBinding mViewBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewBinding = LayoutQuestionBinding.inflate(inflater, container, false);
        setDoubleClickListener(mViewBinding.rootView);

        // for scroll down to hide toolbar, scroll up to show toolbar
        setOnContentScrollListener((OnContentScrollListener) getActivity());
        mViewBinding.scrollView.setOnScrollChangeListener(mScrollChangeListener);
        return mViewBinding.getRoot();
    }

    @Override
    public void loadDate(final String date) {
        final QuestionDao questionDao = getDaoSession().getQuestionDao();
        Question question = questionDao.queryBuilder().where(QuestionDao.Properties.MakeTime.eq(date)).unique();
        if (question != null) {
            if (question.getIsloaded()) {
                mViewBinding.setQuestion(question);
            } else {
                GsonRequest questionItemReq = new GsonRequest<>(Api.URL_QUESTION + question.getId(), OldQuestion.QuestionItem.class, null,
                        new Response.Listener<OldQuestion.QuestionItem>() {
                            @Override
                            public void onResponse(OldQuestion.QuestionItem response) {
                                if ("0".equals(response.res)) {
                                    Question question = new Question();
                                    question.setId(response.getId());
                                    question.setQuestionTitle(response.getQuestionTitle());
                                    question.setQuestionContent(response.getQuestionContent());
                                    question.setAnswerTitle(response.getAnswerTitle());
                                    question.setAnswerContent(response.getAnswerContent());
                                    question.setMakeTime(response.getMakeTime());
                                    question.setEditor(response.getEditor());
                                    question.setWebLink(response.getWebLink());
                                    question.setIsloaded(true);
                                    try {
                                        questionDao.insertOrReplace(question);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    mViewBinding.setQuestion(question);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Taolin", error.getLocalizedMessage());
                            }
                        });
                VolleySingleton.getInstance().addToRequestQueue(questionItemReq);
            }
        }
        final Calendar preloadDate = getPreloadDate(date);
        if (preloadDate != null) {
            Api.requestQuestionData(preloadDate, questionDao);
        }
    }
}
