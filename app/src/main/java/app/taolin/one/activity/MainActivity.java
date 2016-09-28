package app.taolin.one.activity;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import app.taolin.one.App;
import app.taolin.one.R;
import app.taolin.one.fragments.ArticleFragment;
import app.taolin.one.fragments.HomeFragment;
import app.taolin.one.fragments.QuestionFragment;
import app.taolin.one.fragments.SettingsFragment;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.listener.ViewClickListener;

public class MainActivity extends AppCompatActivity implements OnContentScrollListener {

    private static final String TAG_FRAGMENT_HOME = "fragment_home";
    private static final String TAG_FRAGMENT_ARTICLE = "fragment_article";
    private static final String TAG_FRAGMENT_QUESTION = "fragment_question";
    private static final String TAG_FRAGMENT_SETTINGS = "fragment_settings";

    private static final String KEY_SELECTED_BTN_ID = "selected_btn_id";
    private static final int ANIM_DURATION = 200;

    private View mToolbar;
    private TextView mBtnHome;
    private TextView mBtnArticle;
    private TextView mBtnQuestion;
    private TextView mBtnSettings;

    private HomeFragment mHomeFragment;
    private ArticleFragment mArticleFragment;
    private QuestionFragment mQuestionFragment;
    private SettingsFragment mSettingsFragment;

    private FragmentManager mFragmentManager;
    private int mSelectedBtnId;

    private int mToolbarHeight;
    private boolean mIsToolbarHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mSelectedBtnId = R.id.btn_home;
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        clickButton(findViewById(mSelectedBtnId));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mBtnHome = (TextView) findViewById(R.id.btn_home);
        mBtnArticle = (TextView) findViewById(R.id.btn_article);
        mBtnQuestion = (TextView) findViewById(R.id.btn_question);
        mBtnSettings = (TextView) findViewById(R.id.btn_settings);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        mHomeFragment = new HomeFragment();
        transaction.add(R.id.container, mHomeFragment, TAG_FRAGMENT_HOME);
        transaction.hide(mHomeFragment);
        mArticleFragment = new ArticleFragment();
        transaction.add(R.id.container, mArticleFragment, TAG_FRAGMENT_ARTICLE);
        transaction.hide(mArticleFragment);
        mQuestionFragment = new QuestionFragment();
        transaction.add(R.id.container, mQuestionFragment, TAG_FRAGMENT_QUESTION);
        transaction.hide(mQuestionFragment);
        mSettingsFragment = new SettingsFragment();
        transaction.add(R.id.container, mSettingsFragment, TAG_FRAGMENT_SETTINGS);
        transaction.hide(mSettingsFragment);
        transaction.commit();

        mToolbarHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_height);
        mIsToolbarHide = false;
    }

    private void initListener() {
        mBtnHome.setOnClickListener(mClickListener);
        mBtnArticle.setOnClickListener(mClickListener);
        mBtnQuestion.setOnClickListener(mClickListener);
        mBtnSettings.setOnClickListener(mClickListener);
    }

    private void clickButton(View btn) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            btn.performLongClick();
        } else {
            btn.callOnClick();
        }
    }

    private void selectToolbarBtn(int id) {
        mBtnHome.setSelected(false);
        mBtnArticle.setSelected(false);
        mBtnQuestion.setSelected(false);
        mBtnSettings.setSelected(false);
        findViewById(id).setSelected(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SELECTED_BTN_ID, mSelectedBtnId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(KEY_SELECTED_BTN_ID)) {
            mHomeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(TAG_FRAGMENT_HOME);
            mArticleFragment = (ArticleFragment) mFragmentManager.findFragmentByTag(TAG_FRAGMENT_ARTICLE);
            mQuestionFragment = (QuestionFragment) mFragmentManager.findFragmentByTag(TAG_FRAGMENT_QUESTION);
            mSettingsFragment = (SettingsFragment) mFragmentManager.findFragmentByTag(TAG_FRAGMENT_SETTINGS);
            mSelectedBtnId = savedInstanceState.getInt(KEY_SELECTED_BTN_ID);
        }
    }

    private ViewClickListener mClickListener = new ViewClickListener() {
        @Override
        public void onSingleClick(View v) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.hide(mHomeFragment);
            transaction.hide(mArticleFragment);
            transaction.hide(mQuestionFragment);
            transaction.hide(mSettingsFragment);
            mSelectedBtnId = v.getId();
            switch (mSelectedBtnId) {
                case R.id.btn_home:
                    transaction.show(mHomeFragment);
                    break;

                case R.id.btn_article:
                    transaction.show(mArticleFragment);
                    break;

                case R.id.btn_question:
                    transaction.show(mQuestionFragment);
                    break;

                case R.id.btn_settings:
                    transaction.show(mSettingsFragment);
                    break;
            }
            selectToolbarBtn(mSelectedBtnId);
            transaction.commit();
        }

        @Override
        public void onDoubleClick(View v) {
            switch (v.getId()) {
                case R.id.btn_home:
                    mHomeFragment.setToFirstPage();
                    MobclickAgent.onEvent(App.getInstance(), "double_click_home");
                    break;

                case R.id.btn_article:
                    mArticleFragment.setToFirstPage();
                    MobclickAgent.onEvent(App.getInstance(), "double_click_article");
                    break;

                case R.id.btn_question:
                    mQuestionFragment.setToFirstPage();
                    MobclickAgent.onEvent(App.getInstance(), "double_click_question");
                    break;
            }
        }
    };

    @Override
    public void scrollUp(View v) {
        if (mIsToolbarHide) {
            mToolbar.animate().cancel();
            mToolbar.animate().translationY(0).setDuration(ANIM_DURATION);
            mIsToolbarHide = false;
        }
    }

    @Override
    public void scrollDown(View v) {
        if (!mIsToolbarHide) {
            mToolbar.animate().cancel();
            mToolbar.animate().translationY(mToolbarHeight).setDuration(ANIM_DURATION);
            mIsToolbarHide = true;
        }
    }
}
