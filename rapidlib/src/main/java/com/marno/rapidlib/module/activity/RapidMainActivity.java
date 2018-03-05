package com.marno.rapidlib.module.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.marno.rapidlib.R;
import com.marno.rapidlib.basic.BasicActivity;
import com.marno.rapidlib.entity.Entity1;
import com.marno.rapidlib.entity.TabEntity;
import com.marno.rapidlib.interfaces.IMainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marno on 2016/8/23/15:20.
 * 快速创建主页布局
 */
public abstract class RapidMainActivity extends BasicActivity implements IMainView {

    public CommonTabLayout mTabLayout;
    protected ViewPager mViewPager;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private boolean mIsPager;

    @Override
    protected int getLayout() {
        return mIsPager ? R.layout.rapid_viewpager_activity_main : R.layout.rapid_activity_main;
    }

    @Override
    protected void beforeSetView() {
        mIsPager = isPager();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTabLayout = (CommonTabLayout) findViewById(R.id.tablayout);

        List<TabEntity> tabEntities = getTabEntities();
        if (tabEntities.size() == 0) return;

        if (TextUtils.isEmpty(tabEntities.get(0).mTitle)) {
            mTabLayout.setTextsize(0);
            mTabLayout.setIconHeight(26);
            mTabLayout.setIconWidth(26);
        }
        setTab(mTabLayout);

        ArrayList<Fragment> fragments = new ArrayList<>();
        for (TabEntity entity : tabEntities) {
            fragments.add(entity.mFragment);
            String title = null == entity.mTitle ? "" : entity.mTitle;
            mTabEntities.add(new Entity1(title, entity.mSelectedIcon, entity.mUnSelectedIcon));
        }

        if (mIsPager) {
            initViewPager(fragments);
        } else {
            mTabLayout.setTabData(mTabEntities, this, R.id.fLayout_container, fragments);
        }
    }

    private void initViewPager(final List<Fragment> fragments) {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        });
        scrollTogether();
    }

    private void scrollTogether() {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }


    @Override
    public void onBackPressed() {
//        quitApp();
    }
}
