package cn.biiduu.biiduu.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.biiduu.biiduu.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class SquareHeader extends FrameLayout implements PtrUIHandler {
    private ImageView ivIcon;
    private TextView tvText;
    private AnimationDrawable mAnimationDrawable;
    private static int position;
    private String[] mTexts;

    private void initView() {
        mTexts = getResources().getStringArray(R.array.square_header_text);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_square_header, this);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        ivIcon.setImageResource(R.drawable.animation_list_square_header);
        mAnimationDrawable = (AnimationDrawable) ivIcon.getDrawable();
        tvText = (TextView) findViewById(R.id.tv_text);
    }

    public SquareHeader(Context context) {
        this(context, null, 0);
    }

    public SquareHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        if (position >= mTexts.length - 1) {
            position = 0;
        } else {
            position++;
        }
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        tvText.setText(mTexts[position]);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mAnimationDrawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mAnimationDrawable.stop();
        ivIcon.setImageResource(R.drawable.animation_list_square_header);
        mAnimationDrawable = (AnimationDrawable) ivIcon.getDrawable();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
