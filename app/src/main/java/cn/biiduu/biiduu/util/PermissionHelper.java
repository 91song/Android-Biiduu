package cn.biiduu.biiduu.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionHelper {
    private PermissionHelper() {
        throw new AssertionError();
    }

    @TargetApi(23)
    public static boolean hasPermission(Context context, String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat
                .checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(23)
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat
                .shouldShowRequestPermissionRationale(activity, permission);
    }

    @TargetApi(23)
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    @TargetApi(23)
    public static void checkPermission(Activity activity, String permission, PermissionCallback
            permissionCallback, int requestCode) {
        if (hasPermission(activity, permission)) {
            if (permissionCallback != null) {
                permissionCallback.onPermissionGranted();
            }
        } else {
            if (shouldShowRequestPermissionRationale(activity, permission)) {
                if (permissionCallback != null) {
                    permissionCallback.onShowRationale();
                }
            } else {
                String[] permissions = {permission};
                requestPermissions(activity, permissions, requestCode);
            }
        }
    }

    public interface PermissionCallback {
        void onPermissionGranted();

        void onShowRationale();
    }
}
