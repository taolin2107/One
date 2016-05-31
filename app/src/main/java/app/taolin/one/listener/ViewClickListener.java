package app.taolin.one.listener;

import android.view.View;

/**
 * Created by Taolin on 16/5/28.
 */

public abstract class ViewClickListener implements View.OnClickListener {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    private long mLastClickTime = 0;
    private int mLastClickViewId = 0;

    @Override
    public void onClick(View v) {
        long time = System.currentTimeMillis();
        int viewId = v.getId();
        if (viewId == mLastClickViewId
                && time - mLastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(v);
        } else {
            onSingleClick(v);
        }
        mLastClickTime = time;
        mLastClickViewId = viewId;
    }

    public abstract void onSingleClick(View v);
    public abstract void onDoubleClick(View v);
}
