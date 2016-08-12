package app.taolin.one.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import app.taolin.one.App;
import app.taolin.one.CopyrightActivity;
import app.taolin.one.FontSettingsDialog;
import app.taolin.one.R;
import app.taolin.one.utils.Constants;
import app.taolin.one.utils.SharedPreferenceUtil;
import app.taolin.one.widgets.SmoothSwitch;

/**
 * @author taolin
 * @version v1.0
 * @date May 30, 2016.
 * @description
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private SmoothSwitch mNightMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_settings, container, false);
        root.findViewById(R.id.font_size).setOnClickListener(this);
        root.findViewById(R.id.copyright).setOnClickListener(this);
        root.findViewById(R.id.night_mode_layout).setOnClickListener(this);
        root.findViewById(R.id.clean_cache).setOnClickListener(this);
        root.findViewById(R.id.about).setOnClickListener(this);
        mNightMode = (SmoothSwitch) root.findViewById(R.id.night_mode);
        mNightMode.setChecked(SharedPreferenceUtil.readBoolean(Constants.KEY_NIGHT_MODE));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String, String> map = new HashMap<>(1);
                map.put("night_mode", "" + isChecked);
                MobclickAgent.onEvent(App.getInstance(), "switch_night_mode", map);
                SharedPreferenceUtil.writeBoolean(Constants.KEY_NIGHT_MODE, isChecked);
                getActivity().recreate();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.font_size:
                new FontSettingsDialog().show(getFragmentManager(), "font_settings");
                break;

            case R.id.night_mode_layout:
                mNightMode.setChecked(!mNightMode.isChecked());
                break;

            case R.id.copyright:
                MobclickAgent.onEvent(App.getInstance(), "open_copyright");
                startActivity(new Intent(getActivity(), CopyrightActivity.class));
                break;

            case R.id.clean_cache:
                break;

            case R.id.about:
                break;
        }
    }
}
