package app.taolin.one.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author taolin
 * @version v1.0
 * @date Jun 28, 2016.
 * @description 可以通过setEnabled=false来禁止viewpager 滑动
 */

public class ExtViewPager extends ViewPager {

    public ExtViewPager(Context context) {
        super(context);
    }

    public ExtViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isEnabled() && super.onInterceptTouchEvent(ev);
    }
}
