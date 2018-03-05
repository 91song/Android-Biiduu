package com.marno.rapidlib.interfaces;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * Created by Marno on 2016/10/26/11:25
 * Function：
 * Desc：
 */
public interface IUpdateDataView extends BaseQuickAdapter.RequestLoadMoreListener ,PtrHandler {
    /**
     * 使用BaseRecyclerViewAdapterHelper作为上拉加载的实现方式
     * 如果使用ListView或GridView等需要自己去实现上拉加载更多的逻辑
     * @return BaseRecyclerViewAdapterHelper的实现类
     */
    BaseQuickAdapter getAdapter();

    /**
     * 获取RecyclerView的布局管理器对象，根据自己业务实际情况返回
     * @return RecyclerView的布局管理器对象
     */
    RecyclerView.LayoutManager getLayoutManager();

    /**
     * 获取下拉刷新头布局
     * @return 下拉刷新头
     */
    PtrUIHandler getRefreshHeader();
}
