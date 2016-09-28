package app.taolin.one.adapter;

import android.databinding.BindingAdapter;

import com.android.volley.toolbox.NetworkImageView;

import app.taolin.one.utils.VolleySingleton;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 08, 2016.
 * @description
 */

public class DataBindingAdapters {

    @BindingAdapter("android:src")
    public static void setImageSrc(NetworkImageView imageView, String url) {
        if (url != null) {
            imageView.setImageUrl(url, VolleySingleton.getInstance().getImageLoader());
        }
    }
}
