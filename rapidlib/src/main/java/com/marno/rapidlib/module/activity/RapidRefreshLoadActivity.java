package com.marno.rapidlib.module.activity;

import android.view.View;
import android.view.ViewGroup;

import com.marno.rapidlib.basic.BasicActivity;
import com.marno.rapidlib.delegate.UpdateDataDelegate;
import com.marno.rapidlib.interfaces.IUpdateDataView;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * Create by Marno on 2016/10/26/22:43.
 * Function：Activity继承该类可以实现下拉刷新和上拉加载功能
 * Desc：
 */
public abstract class RapidRefreshLoadActivity extends BasicActivity implements IUpdateDataView {

    private UpdateDataDelegate mDelegate;


    @Override
    protected void beforeInitView() {
        ViewGroup rootView = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);
        mDelegate = new UpdateDataDelegate(rootView);
        mDelegate.initPTR(this,getRefreshHeader());
        mDelegate.initLoad(this,getAdapter(),getLayoutManager());
    }

    // 检测是否可以进行下拉刷新
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    //获取下拉刷新头样式，如果子类不重写该方法，则使用默认的样式
    @Override
    public PtrUIHandler getRefreshHeader() {
        return new PtrClassicDefaultHeader(mContext);
    }
}
