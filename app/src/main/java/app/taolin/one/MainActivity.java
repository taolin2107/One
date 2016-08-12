package app.taolin.one;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.Calendar;

import app.taolin.one.utils.Api;
import app.taolin.one.utils.Constants;
import app.taolin.one.dao.ArticleDao;
import app.taolin.one.dao.DaoMaster;
import app.taolin.one.dao.DaoSession;
import app.taolin.one.dao.HomeDao;
import app.taolin.one.dao.QuestionDao;
import app.taolin.one.fragments.ArticleFragment;
import app.taolin.one.fragments.HomeFragment;
import app.taolin.one.fragments.QuestionFragment;
import app.taolin.one.fragments.SettingsFragment;
import app.taolin.one.listener.OnContentScrollListener;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.utils.SharedPreferenceUtil;
import app.taolin.one.utils.Utils;

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
    private View mLoadingProgress;

    private HomeFragment mHomeFragment;
    private ArticleFragment mArticleFragment;
    private QuestionFragment mQuestionFragment;
    private SettingsFragment mSettingsFragment;

    private FragmentManager mFragmentManager;
    private int mSelectedBtnId;

    private int mToolbarHeight;
    private boolean mIsToolbarHide;

    private HomeDao mHomeDao;
    private ArticleDao mArticleDao;
    private QuestionDao mQuestionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setTheme(this);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mSelectedBtnId = R.id.btn_home;
        initView();
        initListener();

        initDatabase();
        requestData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (SharedPreferenceUtil.readBoolean(Constants.KEY_IS_FIRST_OPEN, true)) {
            Calendar calendar = Calendar.getInstance();
            Api.requestHomeData(calendar, mHomeDao, new Api.DataLoadingListener() {
                @Override
                public void onLoadDone(boolean isSuccess) {
                    if (isSuccess) {
                        SharedPreferenceUtil.writeBoolean(Constants.KEY_IS_FIRST_OPEN, false);
                        clickButton(findViewById(mSelectedBtnId));
                        mLoadingProgress.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            mLoadingProgress.setVisibility(View.GONE);
            clickButton(findViewById(mSelectedBtnId));
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
        mLoadingProgress = findViewById(R.id.loading_view);
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

    private void initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        mHomeDao = daoSession.getHomeDao();
        mArticleDao = daoSession.getArticleDao();
        mQuestionDao = daoSession.getQuestionDao();
    }

    private void requestData() {
        Calendar calendar = Calendar.getInstance();
        Api.requestHomeData(calendar, mHomeDao);
        Api.requestArticleData(calendar, mArticleDao);
        Api.requestQuestionData(calendar, mQuestionDao);
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
            mSelectedBtnId = v.getId();
            switch (mSelectedBtnId) {
                case R.id.btn_home:
                    if (mHomeFragment == null) {
                        mHomeFragment = new HomeFragment();
                        transaction.add(R.id.container, mHomeFragment, TAG_FRAGMENT_HOME);
                    } else {
                        transaction.show(mHomeFragment);
                    }
                    break;

                case R.id.btn_article:
                    if (mArticleFragment == null) {
                        mArticleFragment = new ArticleFragment();
                        transaction.add(R.id.container, mArticleFragment, TAG_FRAGMENT_ARTICLE);
                    } else {
                        transaction.show(mArticleFragment);
                    }
                    break;

                case R.id.btn_question:
                    if (mQuestionFragment == null) {
                        mQuestionFragment = new QuestionFragment();
                        transaction.add(R.id.container, mQuestionFragment, TAG_FRAGMENT_QUESTION);
                    } else {
                        transaction.show(mQuestionFragment);
                    }
                    break;

                case R.id.btn_settings:
                    if (mSettingsFragment == null) {
                        mSettingsFragment = new SettingsFragment();
                        transaction.add(R.id.container, mSettingsFragment, TAG_FRAGMENT_SETTINGS);
                    } else {
                        transaction.show(mSettingsFragment);
                    }
                    break;
            }
            clickToolbar(mSelectedBtnId);
            transaction.commit();
        }

        @Override
        public void onDoubleClick(View v) {
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
