package cn.biiduu.biiduu.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import cn.biiduu.biiduu.BuildConfig;
import cn.biiduu.biiduu.constant.EnumConstants;

public class AppUtil {
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (null != packageManager) {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                if (null != packageInfo) {
                    return packageInfo.versionName;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return BuildConfig.VERSION_NAME;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (null != packageManager) {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                if (null != packageInfo) {
                    return packageInfo.versionCode;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return BuildConfig.VERSION_CODE;
    }

    public static String getStatusName(String status) {
        switch (status) {
            case "STRONG_OUT":
                status = "强出";
                break;
            case "SELL_OUT":
                status = "卖出";
                break;
            case "REDUCE":
                status = "减持";
                break;
            case "WAIT_SEE":
                status = "观望";
                break;
            case "RECOMMEND":
                status = "推荐";
                break;
            case "NEUTRAL":
                status = "中性";
                break;
            case "HOLDINGS":
                status = "增持";
                break;
            case "PURCHASE":
                status = "买入";
                break;
            case "EAT_STRONG":
                status = "强吃";
                break;
        }
        return status;
    }

    public static String getChineseClassify(String classify) {
        if (EnumConstants.CLASSIFY_COIN_RING.equals(classify)) {
            return "币圏";
        } else if (EnumConstants.CLASSIFY_COIN_CHAHAR.equals(classify)) {
            return "币察";
        } else if (EnumConstants.CLASSIFY_COIN_NEWSPAPER.equals(classify)) {
            return "币报";
        }
        return null;
    }
}
