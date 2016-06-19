package app.taolin.one.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.CompoundButton;

import app.taolin.one.R;

public class SmoothSwitch extends CompoundButton {
    private static final long DURATION_ANIM_THUMB_MOVE = 250;
    private Drawable mThumbDrawable;
    private Drawable mTrackDrawable;
    private float mTouchX;
    private int mThumbPosition; // left side to track drawable
    private int mThumbMaxPosition;
    private int mThumbMinPosition = 0;
    private int mSwitchWidth; // without padding
    private int mSwitchHeight;
    private int mSwitchLeft;    // without padding
    private int mSwitchTop;
    private int mSwitchRight;
    private int mSwitchBottom;

    private GestureDetector mGestureDetector;
    private ObjectAnimator mThumbMoveAnimator;

    public SmoothSwitch(Context context) {
        this(context, null);
    }

    public SmoothSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmoothSwitch, 0, 0);
        mThumbDrawable = typedArray.getDrawable(R.styleable.SmoothSwitch_drawableThumb);
        mTrackDrawable = typedArray.getDrawable(R.styleable.SmoothSwitch_drawableTrack);
        mThumbMaxPosition = mTrackDrawable.getIntrinsicWidth() - mThumbDrawable.getIntrinsicWidth();
        setChecked(isChecked());
        typedArray.recycle();

        SwitchGestureListener gestureListener = new SwitchGestureListener();
        mGestureDetector = new GestureDetector(getContext(), gestureListener);
    }

    /**
     * Finer control over layout parameters
     * @param widthMeasureSpec how wide parent limit it to be
     * @param heightMeasureSpec how high parent limit it to be
     */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minw = mTrackDrawable.getIntrinsicWidth() + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndStateCopy(minw, widthMeasureSpec, 0);
        int minh = Math.max(mTrackDrawable.getIntrinsicHeight(), mThumbDrawable.getIntrinsicHeight())
                + getPaddingTop() + getPaddingBottom();
        int h = resolveSizeAndStateCopy(minh, heightMeasureSpec, 0);
        setMeasuredDimension(w, h);
    }

    /**
     * For support SDK version without resolveSizeAndState() method.
     * @param size
     * @param measureSpec
     * @param childMeasuredState
     * @return
     */
    public static int resolveSizeAndStateCopy(int size, int measureSpec, int childMeasuredState) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize =  MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result | (childMeasuredState&MEASURED_STATE_MASK);
    }
    /**
     * If this view doesn't need special control over its size, just override this method
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mSwitchWidth = w - getPaddingLeft() - getPaddingRight();
        mSwitchHeight = h - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTrackDrawable.setBounds(mSwitchLeft, mSwitchTop, mSwitchRight, mSwitchBottom);
        mTrackDrawable.draw(canvas);

        int thumbLeft = mSwitchLeft + mThumbPosition;
        int thumbRight = thumbLeft + mThumbDrawable.getIntrinsicWidth();

        mThumbDrawable.setBounds(thumbLeft, mSwitchTop, thumbRight, mSwitchBottom);
        mThumbDrawable.draw(canvas);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();

        if (mThumbDrawable != null) {
            mThumbDrawable.setState(drawableState);
        }
        if (mTrackDrawable != null) {
            mTrackDrawable.setState(drawableState);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consumed = false;
        if (isEnabled()) {
            consumed = mGestureDetector.onTouchEvent(event);
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                mTouchX = event.getX();
//                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            }
            if (!consumed) {
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        mThumbPosition += (int) (event.getX() - mTouchX);
                        mThumbPosition = mThumbPosition < 0 ? 0 : mThumbPosition;
                        mThumbPosition = mThumbPosition > mThumbMaxPosition ? mThumbMaxPosition : mThumbPosition;
                        mTouchX = event.getX();
                        invalidate();
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (mThumbPosition > (mThumbMinPosition + mThumbMaxPosition) / 2) {
                            animateThumb(true);
                        } else {
                            animateThumb(false);
                        }
                        return true;
                    default:
                }
            }
        }
        return consumed;
    }

    private class SwitchGestureListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            animateThumb(!isChecked());
            return true;
        }
    }

    /**
     * Request a new layout if a property changes that might affect the size or shape of the view
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int switchTop;
        int switchBottom;
        switch (getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
            default:
            case Gravity.TOP:
                switchTop = getPaddingTop();
                switchBottom = switchTop + mSwitchHeight;
                break;

            case Gravity.CENTER_VERTICAL:
                switchTop = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2 - mSwitchHeight / 2;
                switchBottom = switchTop + mSwitchHeight;
                break;

            case Gravity.BOTTOM:
                switchBottom = getHeight() - getPaddingBottom();
                switchTop = switchBottom - mSwitchHeight;
                break;
        }
        mSwitchLeft = getPaddingLeft();
        mSwitchTop = switchTop;
        mSwitchRight = mSwitchLeft + mSwitchWidth;
        mSwitchBottom = switchBottom;
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (checked) {
            mThumbPosition = mThumbMaxPosition;
        } else {
            mThumbPosition = mThumbMinPosition;
        }
        invalidate();
    }

    /**
     * Start animation base on checked state
     * @param checked Switch set to checked or not
     */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void animateThumb(final boolean checked) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (checked) {
                mThumbMoveAnimator = ObjectAnimator.ofInt(this, "thumbPosition",
                        mThumbPosition, mThumbMaxPosition);
                mThumbMoveAnimator.setDuration(DURATION_ANIM_THUMB_MOVE
                        * (mThumbMaxPosition - mThumbPosition) / (mThumbMaxPosition - mThumbMinPosition));
            } else {
                mThumbMoveAnimator = ObjectAnimator.ofInt(this, "thumbPosition",
                        mThumbPosition, 0);
                mThumbMoveAnimator.setDuration(DURATION_ANIM_THUMB_MOVE
                        * mThumbPosition / (mThumbMaxPosition - mThumbMinPosition));
            }
            mThumbMoveAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setChecked(checked);
                }
            });
            mThumbMoveAnimator.start();
        } else {
            setChecked(checked);
        }
    }

    /**
     * Also called by animator
     */
    private void setThumbPosition(int position) {
        mThumbPosition = position;
        invalidate();
    }

    public int getThumbPosition() {
        return mThumbPosition;
    }

    public void setTrackDrawable(Drawable track) {
        mTrackDrawable = track;
        mThumbMaxPosition = mTrackDrawable.getIntrinsicWidth() - mThumbDrawable.getIntrinsicWidth();
        requestLayout();
        invalidate();
    }

    public void setTrackResource(int resId) {
        setTrackDrawable(getContext().getResources().getDrawable(resId));
    }

    public Drawable getTrackDrawable() {
        return  mTrackDrawable;
    }

    public void setThumbDrawable(Drawable thumb) {
        mThumbDrawable = thumb;
        mThumbMaxPosition = mTrackDrawable.getIntrinsicWidth() - mThumbDrawable.getIntrinsicWidth();
        requestLayout();
        invalidate();
    }

    public void setThumbResource(int resId) {
        setThumbDrawable(getContext().getResources().getDrawable(resId));
    }

    public Drawable getThumbDrawable() {
        return mThumbDrawable;
    }
}
