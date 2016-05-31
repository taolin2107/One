package app.taolin.one;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.taolin.one.fragments.ArticleFragment;
import app.taolin.one.fragments.HomeFragment;
import app.taolin.one.fragments.QuestionFragment;
import app.taolin.one.fragments.SettingsFragment;
import app.taolin.one.listener.ViewClickListener;

public class MainActivity extends AppCompatActivity {

    private TextView mBtnHome;
    private TextView mBtnArticle;
    private TextView mBtnQuestion;
    private TextView mBtnSettings;

    private HomeFragment mHomeFragment;
    private ArticleFragment mArticleFragment;
    private QuestionFragment mQuestionFragment;
    private SettingsFragment mSettingsFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        initView();
        initListener();
    }

    private void initView() {
        mBtnHome = (TextView) findViewById(R.id.btn_home);
        mBtnArticle = (TextView) findViewById(R.id.btn_article);
        mBtnQuestion = (TextView) findViewById(R.id.btn_question);
        mBtnSettings = (TextView) findViewById(R.id.btn_settings);
    }

    private void initListener() {
        mBtnHome.setOnClickListener(mClickListener);
        mBtnArticle.setOnClickListener(mClickListener);
        mBtnQuestion.setOnClickListener(mClickListener);
        mBtnSettings.setOnClickListener(mClickListener);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mBtnHome.performLongClick();
        } else {
            mBtnHome.callOnClick();
        }
    }

    private void clickToolbar(int id) {
        mBtnHome.setSelected(false);
        mBtnArticle.setSelected(false);
        mBtnQuestion.setSelected(false);
        mBtnSettings.setSelected(false);
        findViewById(id).setSelected(true);
    }

    private ViewClickListener mClickListener = new ViewClickListener() {
        @Override
        public void onSingleClick(View v) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (mHomeFragment != null) {
                transaction.hide(mHomeFragment);
            }
            if (mArticleFragment != null) {
                transaction.hide(mArticleFragment);
            }
            if (mQuestionFragment != null) {
                transaction.hide(mQuestionFragment);
            }
            if (mSettingsFragment != null) {
                transaction.hide(mSettingsFragment);
            }
            int id = v.getId();
            switch (id) {
                case R.id.btn_home:
                    if (mHomeFragment == null) {
                        mHomeFragment = new HomeFragment();
                        transaction.add(R.id.container, mHomeFragment);
                    } else {
                        transaction.show(mHomeFragment);
                    }
                    clickToolbar(id);
                    break;

                case R.id.btn_article:
                    if (mArticleFragment == null) {
                        mArticleFragment = new ArticleFragment();
                        transaction.add(R.id.container, mArticleFragment);
                    } else {
                        transaction.show(mArticleFragment);
                    }
                    clickToolbar(id);
                    break;

                case R.id.btn_question:
                    if (mQuestionFragment == null) {
                        mQuestionFragment = new QuestionFragment();
                        transaction.add(R.id.container, mQuestionFragment);
                    } else {
                        transaction.show(mQuestionFragment);
                    }
                    clickToolbar(id);
                    break;

                case R.id.btn_settings:
                    if (mSettingsFragment == null) {
                        mSettingsFragment = new SettingsFragment();
                        transaction.add(R.id.container, mSettingsFragment);
                    } else {
                        transaction.show(mSettingsFragment);
                    }
                    clickToolbar(id);
                    break;
            }
            transaction.commit();
        }

        @Override
        public void onDoubleClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_home:
                    mHomeFragment.setToFirstPage();
                    break;

                case R.id.btn_article:
                    mArticleFragment.setToFirstPage();
                    break;

                case R.id.btn_question:
                    mQuestionFragment.setToFirstPage();
                    break;
            }
        }
    };
}
