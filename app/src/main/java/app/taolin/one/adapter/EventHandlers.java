package app.taolin.one.adapter;

import android.app.Activity;
import android.view.View;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 22, 2016.
 * @description
 */

public class EventHandlers {

    public void onBackClick(View view) {
        if (view.getContext() instanceof Activity) {
            ((Activity) view.getContext()).finish();
        }
    }
}
