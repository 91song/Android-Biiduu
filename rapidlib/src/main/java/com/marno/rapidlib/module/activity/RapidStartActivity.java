package com.marno.rapidlib.module.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.marno.easyutilcode.AppUtil;
import com.marno.easyutilcode.SPUtil;
import com.marno.rapidlib.R;
import com.marno.rapidlib.basic.BasicActivity;

/**
 * Create by Marno on 2016/11/2 15:53
 * Function：启动页，引导页
 * Desc：
 */
public class RapidStartActivity extends BasicActivity {

    private String
            F_RAPID = "rapidFile",
            IS_FIRST_RUN = "isFirstRun",
            VERSION_NAME = "versionName";


    private boolean mIsFirstRun; //是否第一次启动app

    private String mLastVersion; //上次更新app版本
    private String mNowVersion;//当前app版本

    @Override
    protected int getLayout() {
        return R.layout.activity_rapid_start;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mIsFirstRun = (boolean) SPUtil.get(F_RAPID, this, IS_FIRST_RUN, true);
        mLastVersion = (String) SPUtil.get(F_RAPID, this, VERSION_NAME, "1.0.0");
        mNowVersion = AppUtil.getVersionName(mContext);
    }

    @Override
    public void onBackPressed() {

    }
}
