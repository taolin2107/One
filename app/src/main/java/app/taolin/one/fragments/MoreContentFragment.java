package app.taolin.one.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.taolin.one.App;
import app.taolin.one.activity.MoreContentListActivity;
import app.taolin.one.activity.MoreHomeListActivity;
import app.taolin.one.R;
import app.taolin.one.listener.ViewClickListener;
import app.taolin.one.utils.Constants;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 13, 2016.
 * @description
 */

public class MoreContentFragment extends Fragment {

    public static MoreContentFragment getInstance(int type) {
        MoreContentFragment fragment = new MoreContentFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.PARAMS_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.more_content_date_list_layout, container, false);
        final ListView listView = (ListView) root.findViewById(R.id.date_list);
        final List<String> dateList = new ArrayList<>();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(App.getInstance(),
                R.layout.more_content_date_list_item, R.id.text, dateList);
        listView.setAdapter(listAdapter);
        root.findViewById(R.id.titlebar).setOnClickListener(new ViewClickListener() {
            @Override
            public void onSingleClick(View v) {}

            @Override
            public void onDoubleClick(View v) {
                listView.smoothScrollToPosition(0);
            }
        });
        final int type = getArguments().getInt(Constants.PARAMS_TYPE);
        TextView titleText = (TextView) root.findViewById(R.id.title_text);
        switch (type) {
            case Constants.TYPE_HOME:
                titleText.setText(R.string.text_home);
                break;
            case Constants.TYPE_ARTICLE:
                titleText.setText(R.string.text_article);
                break;
            case Constants.TYPE_QUESTION:
                titleText.setText(R.string.text_question);
                break;
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (Constants.TYPE_HOME == type) {
                    intent = new Intent(getActivity(), MoreHomeListActivity.class);
                } else {
                    intent = new Intent(getActivity(), MoreContentListActivity.class);
                }
                intent.putExtra(Constants.PARAMS_TYPE, type);
                intent.putExtra(Constants.PARAMS_DATE, dateList.get(position));
                startActivity(intent);
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.US);
        Calendar calendar = Calendar.getInstance();
        dateList.add(dateFormat.format(calendar.getTime()));
        while (true) {
            calendar.add(Calendar.MONTH, -1);
            dateList.add(dateFormat.format(calendar.getTime()));
            if (calendar.get(Calendar.YEAR) == 2012
                    && calendar.get(Calendar.MONTH) == 9) {   //2012年10月之后才有内容,月份从0算,所以减1
                listAdapter.notifyDataSetChanged();
                break;
            }
        }
        return root;
    }
}
