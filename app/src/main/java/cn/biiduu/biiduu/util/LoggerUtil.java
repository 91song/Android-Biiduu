package cn.biiduu.biiduu.util;

import com.orhanobut.logger.Logger;

import cn.biiduu.biiduu.BuildConfig;

public class LoggerUtil {
    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.i(msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.d(msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.e(msg);
        }
    }

    public static void json(String jsonStr) {
        if (BuildConfig.DEBUG) {
            Logger.json(jsonStr);
        }
    }
}
