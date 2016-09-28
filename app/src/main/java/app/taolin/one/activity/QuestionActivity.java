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
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.dao.Question;
import app.taolin.one.dao.QuestionDao;
import app.taolin.one.databinding.LayoutQuestionBinding;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.models.OldQuestion;
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

public class QuestionActivity extends BaseActivity {

    private LayoutQuestionBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = DataBindingUtil.setContentView(this, R.layout.layout_question);
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

    @Override
    void loadData(String id) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        final QuestionDao questionDao =  daoMaster.newSession().getQuestionDao();
        Question question = questionDao.queryBuilder().where(QuestionDao.Properties.Id.eq(id)).unique();
        if (question != null && question.getIsloaded()) {
            mViewBinding.setQuestion(question);
        } else {
            final String BaseMoreUrl = Utils.encryto(Utils.reverse(Api.URL_MORE_BASE), -9);
            GsonRequest request = new GsonRequest<>(BaseMoreUrl + Api.URL_MORE_QUESTION + id, OldQuestion.QuestionItem.class, null,
                    new Response.Listener<OldQuestion.QuestionItem>() {
                        @Override
                        public void onResponse(OldQuestion.QuestionItem response) {
                            if ("0".equals(response.res)) {
                                OldQuestion.Data a = response.data;
                                Question question = new Question();
                                question.setId(a.getId());
                                question.setQuestionTitle(a.getQuestionTitle());
                                question.setQuestionContent(a.getQuestionContent());
                                question.setAnswerTitle(a.getAnswerTitle());
                                question.setAnswerContent(a.getAnswerContent());
                                question.setMakeTime(a.getMakeTime());
                                question.setEditor(a.getEditor());
                                question.setWebLink(a.getWebLink());
                                question.setGuideWord(a.getGuideWord());
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
            VolleySingleton.getInstance().addToRequestQueue(request);
        }
    }
}
