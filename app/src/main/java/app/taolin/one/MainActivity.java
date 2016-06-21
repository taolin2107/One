package app.taolin.one;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import app.taolin.one.fragments.ArticleFragment;
import app.taolin.one.fragments.HomeFragment;
import app.taolin.one.fragments.QuestionFragment;
import app.taolin.one.fragments.SettingsFragment;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.utils.Utils;

public class MainActivity extends AppCompatActivity implements OnContentScrollListener {

    private static final String TAG_FRAGMENT_HOME = "fragment_home";
    private static final String TAG_FRAGMENT_ARTICLE = "fragment_article";
    private static final String TAG_FRAGMENT_QUESTION = "fragment_question";
    private static final String TAG_FRAGMENT_SETTINGS = "fragment_settings";

    private static final String KEY_CURRENT_INDEX = "current_index";
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
    private int mCurrentIndex;

    private int mToolbarHeight;
    private boolean mIsToolbarHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setTheme(this);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mCurrentIndex = 0;
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        switch (mCurrentIndex) {
            case 0:
                clickButton(mBtnHome);
                break;
            case 1:
                clickButton(mBtnArticle);
                break;
            case 2:
                clickButton(mBtnQuestion);
                break;
            case 3:
                clickButton(mBtnSettings);
                break;
        }
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

    private void clickToolbar(int id) {
        mBtnHome.setSelected(false);
        mBtnArticle.setSelected(false);
        mBtnQuestion.setSelected(false);
        mBtnSettings.setSelected(false);
        findViewById(id).setSelected(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_CURRENT_INDEX, mCurrentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(KEY_CURRENT_INDEX)) {
            mHomeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(TAG_FRAGMENT_HOME);
            mArticleFragment = (ArticleFragment) mFragmentManager.findFragmentByTag(TAG_FRAGMENT_ARTICLE);
            mQuestionFragment = (QuestionFragment) mFragmentManager.findFragmentByTag(TAG_FRAGMENT_QUESTION);
            mSettingsFragment = (SettingsFragment) mFragmentManager.findFragmentByTag(TAG_FRAGMENT_SETTINGS);
            mCurrentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
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
                        transaction.add(R.id.container, mHomeFragment, TAG_FRAGMENT_HOME);
                    } else {
                        transaction.show(mHomeFragment);
                    }
                    clickToolbar(id);
                    mCurrentIndex = 0;
                    break;

                case R.id.btn_article:
                    if (mArticleFragment == null) {
                        mArticleFragment = new ArticleFragment();
                        transaction.add(R.id.container, mArticleFragment, TAG_FRAGMENT_ARTICLE);
                    } else {
                        transaction.show(mArticleFragment);
                    }
                    clickToolbar(id);
                    mCurrentIndex = 1;
                    break;

                case R.id.btn_question:
                    if (mQuestionFragment == null) {
                        mQuestionFragment = new QuestionFragment();
                        transaction.add(R.id.container, mQuestionFragment, TAG_FRAGMENT_QUESTION);
                    } else {
                        transaction.show(mQuestionFragment);
                    }
                    clickToolbar(id);
                    mCurrentIndex = 2;
                    break;

                case R.id.btn_settings:
                    if (mSettingsFragment == null) {
                        mSettingsFragment = new SettingsFragment();
                        transaction.add(R.id.container, mSettingsFragment, TAG_FRAGMENT_SETTINGS);
                    } else {
                        transaction.show(mSettingsFragment);
                    }
                    clickToolbar(id);
                    mCurrentIndex = 3;
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
