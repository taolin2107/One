package app.taolin.one.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * @author taolin
 * @version v1.0
 * @date Jun 15, 2016.
 * @description
 */

public class ScrollViewExt extends ScrollView {

    public interface OnScrollChangeListener {
        void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    private OnScrollChangeListener mScrollChangeListener;

    public ScrollViewExt(Context context) {
        super(context);
    }

    public ScrollViewExt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewExt(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener scrollListener) {
        mScrollChangeListener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollChangeListener != null) {
            mScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }
}
