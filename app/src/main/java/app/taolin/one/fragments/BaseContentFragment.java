package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ScrollView;

import app.taolin.one.R;
import app.taolin.one.common.Constants;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.utils.DateUtil;

/**
 * Created by Taolin on 16/5/27.
 */

public abstract class BaseContentFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        String date = bundle.getString(Constants.PARAMS_DATE);
        int index = bundle.getInt(Constants.PARAMS_INDEX);
        int ms = bundle.getInt(Constants.PARAMS_MS);
        loadDate(date, index, ms);
    }

    public static Bundle getArguments(int index) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PARAMS_DATE, DateUtil.getDateString());
        bundle.putInt(Constants.PARAMS_INDEX, index + 1);   //index从1开始
        bundle.putInt(Constants.PARAMS_MS, 1);
        return bundle;
    }

    public void setDoubleClickListener(View root) {
        final ScrollView contentScroll = (ScrollView) root.findViewById(R.id.scroll_view);
        root.findViewById(R.id.double_click_area).setOnClickListener(new ViewClickListener() {
            @Override
            public void onSingleClick(View v) {
            }

            @Override
            public void onDoubleClick(View v) {
                contentScroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    public abstract void loadDate(String date, int row, int ms);
}
