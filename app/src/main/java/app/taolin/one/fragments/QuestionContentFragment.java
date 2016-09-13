package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.taolin.one.dao.Question;
import app.taolin.one.dao.QuestionDao;
import app.taolin.one.databinding.LayoutQuestionBinding;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.models.LatestQuestion;
import app.taolin.one.utils.OneServiceSingleton;
import retrofit2.Call;
import retrofit2.Callback;

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
    public void loadDate(final String date, final int row, final int ms) {
        final QuestionDao questionDao = getDaoSession().getQuestionDao();
        Question question = questionDao.queryBuilder().where(QuestionDao.Properties.MakeTime.eq(date)).unique();
        if (question != null) {
            mViewBinding.setQuestion(question);
            return;
        }
        //这个api只和row有关,date没用
        Call<LatestQuestion> getQuestion = OneServiceSingleton.getInstance().mOneService.getQuestion(date, row);
        getQuestion.enqueue(new Callback<LatestQuestion>() {
            @Override
            public void onResponse(Call<LatestQuestion> call, retrofit2.Response<LatestQuestion> response) {
                LatestQuestion latestQuestion = response.body();
                if ("SUCCESS".equals(latestQuestion.result)) {
                    Question question = new Question();
                    question.setId(latestQuestion.getId());
                    question.setQuestionTitle(latestQuestion.getQuestionTitle());
                    question.setQuestionContent(latestQuestion.getQuestionContent());
                    question.setAnswerTitle(latestQuestion.getAnswerTitle());
                    question.setAnswerContent(latestQuestion.getAnswerContent());
                    question.setMakeTime(latestQuestion.getMakeTime());
                    question.setEditor(latestQuestion.getEditor());
                    question.setWebLink(latestQuestion.getWebLink());
                    question.setIsloaded(true);
                    try {
                        questionDao.insertOrReplace(question);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mViewBinding.setQuestion(question);
                }
            }

            @Override
            public void onFailure(Call<LatestQuestion> call, Throwable t) {
                Log.e("QuestionContentFragment", t + "");
            }
        });
    }
}
