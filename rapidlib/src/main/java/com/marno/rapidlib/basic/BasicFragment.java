package com.marno.rapidlib.basic;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Marno on 2016/3/14/13:53.
 * 所有Fragment的基类
 */
public abstract class BasicFragment extends RxFragment {

    protected String TAG = this.getClass().getSimpleName();

    protected Activity mContext;

    protected boolean mIsFirstShow;

    private Unbinder mUnbinder;

    protected View mContentView;

    protected abstract int getLayout();

    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 在初始化视图前做一些操作
     */
    protected void beforeInitView() {
    }

    /**
     * 需要加载数据时重写此方法
     */
    protected void loadData() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
        mIsFirstShow = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mContentView);
        beforeInitView();
        initView(mContentView, savedInstanceState);
        return mContentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 不在viewpager中Fragment懒加载
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) lazyLoad();
    }

    /**
     * 在viewpager中的Fragment懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) lazyLoad();
    }

    private void lazyLoad() {
        if (mIsFirstShow) {
            mIsFirstShow = false;
            loadData();
        }
    }
}
