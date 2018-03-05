package cn.biiduu.biiduu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class MPtrFrameLayout extends PtrFrameLayout {
    public MPtrFrameLayout(Context context) {
        super(context);
    }

    public MPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float startY;
    private float startX;
    private boolean mIsHorizontalMove;
    private boolean isDeal;
    private boolean needHorizontalMove;
    private int mTouchSlop;

    @Override
    public void disableWhenHorizontalMove(boolean disable) {
        super.disableWhenHorizontalMove(disable);
        this.needHorizontalMove = disable;
        if (needHorizontalMove) return;
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!needHorizontalMove) return super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                startX = ev.getX();
                mIsHorizontalMove = false;
                isDeal = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDeal) {
                    break;
                }
                mIsHorizontalMove = true;
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (distanceX != distanceY) {
                    if (distanceX > mTouchSlop && distanceX > distanceY) {
                        mIsHorizontalMove = true;
                        isDeal = true;
                    } else if (distanceY > mTouchSlop) {
                        mIsHorizontalMove = false;
                        isDeal = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsHorizontalMove = false;
                isDeal = false;
                break;
        }
        if (mIsHorizontalMove) {
            return dispatchTouchEventSupper(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}