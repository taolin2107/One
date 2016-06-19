package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.Date;

import app.taolin.one.R;
import app.taolin.one.models.Home;
import app.taolin.one.utils.DateUtil;
import app.taolin.one.utils.OneServiceSingleton;
import app.taolin.one.utils.VolleySingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void loadDate(final String date, final int row, final int ms) {
        Call<Home> getPhoto = OneServiceSingleton.getInstance().mOneService.getPhoto(date, row);
        getPhoto.enqueue(new Callback<Home>() {
            @Override
            public void onResponse(Call<Home> call, Response<Home> response) {
                Home photo = response.body();
                if ("SUCCESS".equals(photo.result)) {
                    mTitle.setText(photo.hpEntity.strHpTitle);
                    mPhoto.setImageUrl(photo.hpEntity.strOriginalImgUrl, VolleySingleton.getInstance().getImageLoader());
                    mAuthor.setText(photo.hpEntity.strAuthor.replace("&", "\n"));
                    Date date = DateUtil.getDate(photo.hpEntity.strMarketTime);
                    mDay.setText(DateUtil.getDay(date));
                    mMonthYear.setText(DateUtil.getMonthYear(date));
                    mContent.setText(photo.hpEntity.strContent.trim());
                }
            }

            @Override
            public void onFailure(Call<Home> call, Throwable t) {
                Log.e("HomeContentFragment", t + "");
            }
        });
    }
}
