package app.taolin.one.fragments;

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

import java.util.Calendar;

import app.taolin.one.R;
import app.taolin.one.dao.Question;
import app.taolin.one.dao.QuestionDao;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.models.QuestionModel;
import app.taolin.one.utils.Api;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.utils.GsonRequest;
import app.taolin.one.utils.VolleySingleton;
import app.taolin.one.widgets.ScrollViewExt;

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

        // for scroll down to hide toolbar, scroll up to show toolbar
        setOnContentScrollListener((OnContentScrollListener) getActivity());
        ScrollViewExt contentScroller = (ScrollViewExt) root.findViewById(R.id.scroll_view);
        contentScroller.setOnScrollChangeListener(mScrollChangeListener);
        return root;
    }

    private void updateViews(Question question) {
        mDate.setText(DateUtil.getDisplayDate(question.getMakettime()));
        mQuestion.setText(question.getQuestiontitle());
        mQuestionDesc.setText(question.getQuestioncontent());
        mAnswerTitle.setText(question.getAnswertitle());
        mAnswer.setText(Html.fromHtml(question.getAnswercontent()));
        mEditor.setText(question.getEditor());
    }

    @Override
    public void loadDate(final String date) {
        final QuestionDao questionDao = getDaoSession().getQuestionDao();
        Question question = questionDao.queryBuilder().where(QuestionDao.Properties.Makettime.eq(date)).unique();
        if (question != null) {
            if (question.getIsloaded()) {
                updateViews(question);
            } else {
                GsonRequest questionItemReq = new GsonRequest<>(Api.URL_QUESTION + question.getId(), QuestionModel.QuestionItem.class, null,
                        new Response.Listener<QuestionModel.QuestionItem>() {
                            @Override
                            public void onResponse(QuestionModel.QuestionItem response) {
                                if ("0".equals(response.res)) {
                                    QuestionModel.Data q = response.data;
                                    Question question = new Question();
                                    question.setId(q.question_id);
                                    question.setQuestiontitle(q.question_title);
                                    question.setQuestioncontent(q.question_content);
                                    question.setAnswertitle(q.answer_title);
                                    question.setAnswercontent(q.answer_content);
                                    question.setMakettime(q.question_makettime.substring(0, 10));
                                    question.setEditor(q.charge_edt);
                                    question.setUpdatedate(q.last_update_date);
                                    question.setWeburl(q.web_url);
                                    question.setReadnum(q.read_num);
                                    question.setGuideword(q.guide_word);
                                    question.setPraisenum(q.praisenum);
                                    question.setSharenum(q.sharenum);
                                    question.setCommentnum(q.commentnum);
                                    question.setIsloaded(true);
                                    try {
                                        questionDao.insertOrReplace(question);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    updateViews(question);
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
