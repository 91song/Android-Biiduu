package com.marno.rapidlib.entity;

import android.support.v4.app.Fragment;

/**
 * Create by Marno on 2016/11/29 16:45
 * Function：tab实体类
 * Desc：
 */
public class TabEntity {
    public String mTitle;
    public int mSelectedIcon;
    public int mUnSelectedIcon;
    public Fragment mFragment;

    public TabEntity(String title, int unSelectedIcon, int selectedIcon, Fragment fragment) {
        this.mTitle = title;
        this.mSelectedIcon = selectedIcon;
        this.mUnSelectedIcon = unSelectedIcon;
        this.mFragment = fragment;
    }

    public TabEntity(int unSelectedIcon, int selectedIcon, Fragment fragment) {
        mSelectedIcon = selectedIcon;
        mUnSelectedIcon = unSelectedIcon;
        mFragment = fragment;
    }
}
