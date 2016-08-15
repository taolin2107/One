package app.taolin.one.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import app.taolin.one.AboutActivity;
import app.taolin.one.CopyrightActivity;
import app.taolin.one.FontSettingsDialog;
import app.taolin.one.R;
import app.taolin.one.utils.CommonUtil;
import app.taolin.one.utils.Constants;
import app.taolin.one.utils.DiskLruCache;
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
    private TextView mCacheSizeText;
    private Context mContext;
    private DiskLruCache mDiskCache;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

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
        mCacheSizeText = (TextView) root.findViewById(R.id.cache_size);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferenceUtil.writeBoolean(Constants.KEY_NIGHT_MODE, isChecked);
                getActivity().recreate();
            }
        });
        initCache();
        refreshCacheSize();
    }

    private void refreshCacheSize() {
        long cacheSize = mDiskCache.size();
        if (cacheSize > 0) {
            mCacheSizeText.setText(formatFileSize(cacheSize));
            mCacheSizeText.setVisibility(View.VISIBLE);
        } else {
            mCacheSizeText.setVisibility(View.GONE);
        }
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
                startActivity(new Intent(mContext, CopyrightActivity.class));
                break;

            case R.id.clean_cache:
                try {
                    mDiskCache.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                initCache();
                refreshCacheSize();
                Toast.makeText(mContext, R.string.clean_cache_success, Toast.LENGTH_SHORT).show();
                break;

            case R.id.about:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mDiskCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCache() {
        try {
            File cacheDir = CommonUtil.getDiskCacheDir(mContext);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskCache = DiskLruCache.open(cacheDir, CommonUtil.getAppVersion(mContext), 1, Constants.MAX_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatFileSize(long size) {
        final long B = 1;
        final long K = 1024 * B;
        final long M = 1024 * K;
        final long G = 1024 * M;
        if (size < K) {
            return size + " B";
        } else if (size < M) {
            return String.format(Locale.US, "%.02f K", size * 1f/ K);
        } else if (size < G) {
            return String.format(Locale.US, "%.02f M", size * 1f/ M);
        } else {
            return String.format(Locale.US, "%.02f G", size * 1f/ G);
        }
    }
}
