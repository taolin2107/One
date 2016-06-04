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
import app.taolin.one.models.Question;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.utils.OneServiceSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Taolin on 16/5/28.
 */

public class QuestionContentFragment extends BaseContentFragment {

    private TextView mDate;
    private TextView mQuestion;
    private TextView mQuestionDesc;
    private TextView mAnswer;
    private TextView mAnswerTitle;
    private TextView mEditor;

    public static QuestionContentFragment getInstance(int index) {
        QuestionContentFragment fragment = new QuestionContentFragment();
        fragment.setArguments(getArguments(index));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_question, container, false);
        mDate = (TextView) root.findViewById(R.id.date);
        mQuestion = (TextView) root.findViewById(R.id.question);
        mQuestionDesc = (TextView) root.findViewById(R.id.question_desc);
        mAnswer = (TextView) root.findViewById(R.id.answer);
        mEditor = (TextView) root.findViewById(R.id.editor);
        mAnswerTitle = (TextView) root.findViewById(R.id.answer_title);
        setDoubleClickListener(root);
        return root;
    }

    @Override
    public void loadDate(final String date, final int row, final int ms) {
        Call<Question> getArticle = OneServiceSingleton.getInstance().mOneService.getQuestion(date, row);
        getArticle.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                Question question = response.body();
                if ("SUCCESS".equals(question.result)) {
                    mDate.setText(DateUtil.getDisplayDate(question.questionAdEntity.strQuestionMarketTime));
                    mQuestion.setText(question.questionAdEntity.strQuestionTitle);
                    mQuestionDesc.setText(question.questionAdEntity.strQuestionContent);
                    mAnswerTitle.setText(question.questionAdEntity.strAnswerTitle);
                    mAnswer.setText(Html.fromHtml(question.questionAdEntity.strAnswerContent));
                    mEditor.setText(question.questionAdEntity.sEditor);
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Log.e("QuestionContentFragment", t + "");
            }
        });
    }
}
