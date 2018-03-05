package cn.biiduu.biiduu.util;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    public static void showCenterToast(Context context, int resId, int duration) {
        Toast toast = new Toast(context);
        TextView textView = new TextView(context);
        textView.setText(resId);
        textView.setTextSize(16);
        textView.setPadding(60, 30, 60, 30);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.BLACK);
        toast.setView(textView);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
