package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.Date;

import app.taolin.one.R;
import app.taolin.one.utils.Api;
import app.taolin.one.dao.Home;
import app.taolin.one.dao.HomeDao;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.utils.VolleySingleton;

/**
 * Created by Taolin on 16/5/27.
 */

public class HomeContentFragment extends BaseContentFragment {

    private TextView mTitle;
    private NetworkImageView mPhoto;
    private TextView mAuthor;
    private TextView mDay;
    private TextView mMonthYear;
    private TextView mContent;

    public static HomeContentFragment getInstance(int index) {
        HomeContentFragment fragment = new HomeContentFragment();
        fragment.setArguments(getArguments(index));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_home, container, false);
        mTitle = (TextView) root.findViewById(R.id.title);
        mPhoto = (NetworkImageView) root.findViewById(R.id.photo);
        mAuthor = (TextView) root.findViewById(R.id.author);
        mDay = (TextView) root.findViewById(R.id.day);
        mMonthYear = (TextView) root.findViewById(R.id.month_year);
        mContent = (TextView) root.findViewById(R.id.content);
        setDoubleClickListener(root);
        return root;
    }

    @Override
    public void loadDate(final String date) {
        final long startTime = System.currentTimeMillis();
        final HomeDao homeDao = getDaoSession().getHomeDao();
        Home home = homeDao.queryBuilder().where(HomeDao.Properties.Makettime.eq(date)).unique();
        if (home != null) {
            mTitle.setText(home.getTitle());
            mPhoto.setImageUrl(home.getImgurl(), VolleySingleton.getInstance().getImageLoader());
            mAuthor.setText(home.getAuthor().replace("&", "\n"));
            Date maketDate = DateUtil.getDate(home.getMakettime());
            mDay.setText(DateUtil.getDay(maketDate));
            mMonthYear.setText(DateUtil.getMonthYear(maketDate));
            mContent.setText(home.getContent().trim());
        }
        loadDone(startTime);
        final String preloadDate = getPreloadMonth(date);
        if (preloadDate != null) {
            Api.requestHomeData(preloadDate, homeDao);
        }
    }
}
