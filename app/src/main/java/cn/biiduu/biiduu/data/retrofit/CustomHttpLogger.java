package cn.biiduu.biiduu.data.retrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.logging.HttpLoggingInterceptor;

public class CustomHttpLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(@NonNull String message) {
        String tag = "OkHttp";
        if (!message.startsWith("{")) {
            d(tag, message);
            return;
        }
        try {
            String prettyPrintJson = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message));
            d(tag, prettyPrintJson);
        } catch (JsonSyntaxException m) {
            d(tag, message);
        }
    }

    private void d(String tag, String msg) {
        int logMaxLength = 2000;
        int strLength = msg.length();
        int start = 0;
        int end = logMaxLength;
        for (int i = 0; i < 100; i++) {
            if (strLength > end) {
                Log.d(tag + i, msg.substring(start, end));
                start = end;
                end = end + logMaxLength;
            } else {
                Log.d(tag, msg.substring(start, strLength));
                break;
            }
        }
    }
}