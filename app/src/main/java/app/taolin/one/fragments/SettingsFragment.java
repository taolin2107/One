package app.taolin.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import app.taolin.one.App;
import app.taolin.one.BuildConfig;
import app.taolin.one.R;
import app.taolin.one.common.Constants;
import app.taolin.one.utils.SharedPreferenceUtil;
import app.taolin.one.widgets.SmoothSwitch;

/**
 * @author taolin
 * @version v1.0
 * @date May 30, 2016.
 * @description
 */

public class SettingsFragment extends Fragment {

    private Spinner mTextSize;
    private SmoothSwitch mNightMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_settings, container, false);
        mTextSize = (Spinner) root.findViewById(R.id.font_size);
        mNightMode = (SmoothSwitch) root.findViewById(R.id.night_mode);
        final TextView versionCode = (TextView) root.findViewById(R.id.version_code);
        versionCode.setText(BuildConfig.VERSION_NAME);
        mTextSize.setAdapter(new ArrayAdapter<>(App.getInstance(), SharedPreferenceUtil
                .readBoolean(Constants.KEY_NIGHT_MODE) ? R.layout.drop_down_item_layout_dark:
                R.layout.drop_down_item_layout, android.R.id.text1,
                App.getInstance().getResources().getStringArray(R.array.text_size_items)));
        mTextSize.setSelection(SharedPreferenceUtil.readInt(Constants.KEY_FONT_SIZE, 1));
        mNightMode.setChecked(SharedPreferenceUtil.readBoolean(Constants.KEY_NIGHT_MODE));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTextSize.post(new Runnable() {
            @Override
            public void run() {
                mTextSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferenceUtil.writeInt(Constants.KEY_FONT_SIZE, position);
                        getActivity().recreate();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
        mNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferenceUtil.writeBoolean(Constants.KEY_NIGHT_MODE, isChecked);
                getActivity().recreate();
            }
        });
    }
}
