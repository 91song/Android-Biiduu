package cn.biiduu.biiduu.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.reflect.Field;

public class HeaderViewPager extends ViewPager {
    public HeaderViewPager(Context context) {
        super(context);
    }

    public HeaderViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /*
         * 坑，解决在RecyclerView中使用的bug
         * 设ViewPager中有3张照片
         * 当ViewPager滑动一遍之后，向下滑动RecyclerView列表
         * 直到完全隐藏此ViewPager，并执行了onDetachedFromWindow
         * 再回来时，将会出现bug，第一次滑动时没有动画效果，并且，经常出现view没有加载的情况
         */
        try {
            Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
