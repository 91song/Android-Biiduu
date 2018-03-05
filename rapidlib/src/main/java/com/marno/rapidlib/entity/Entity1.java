package com.marno.rapidlib.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Marno on 2016/11/29/17:27
 * Function：内部初始化tab的实体，不公开
 * Desc：
 */
public class Entity1 implements CustomTabEntity {
    public String mTitle;
    public int mSelectedIcon;
    public int mUnSelectedIcon;


    public Entity1(String title, int selectedIcon, int unSelectedIcon) {
        this.mTitle = title;
        this.mSelectedIcon = selectedIcon;
        this.mUnSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return mTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return mSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return mUnSelectedIcon;
    }
}
