package cn.biiduu.biiduu;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.biiduu.biiduu.util.ShareManager;

public class BiiduuApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initUmeng();
    }

    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initUmeng() {
        ShareManager.getInstance().init(this);
    }
}
