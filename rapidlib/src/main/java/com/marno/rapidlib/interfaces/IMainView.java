package com.marno.rapidlib.interfaces;

import com.flyco.tablayout.CommonTabLayout;
import com.marno.rapidlib.entity.TabEntity;

import java.util.List;

/**
 * Created by marno on 2016/8/28/09:29.
 */
public interface IMainView {

    /**
     * 控制主页面是否可以左右滑动
     *
     * @return true - 可以滑动切换，类似微信;false - 不可滑动切换，类似QQ
     */
    boolean isPager();

    /**
     * 添加tab
     */
    List<TabEntity> getTabEntities();

    /**
     * 如果需要手动修改tab的一些属性，可以在这里设置
     * 比如控制字体大小，显示未读消息，更改字体颜色等
     */
    void setTab(CommonTabLayout tabLayout);
}
