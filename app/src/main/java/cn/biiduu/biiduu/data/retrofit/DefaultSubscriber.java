package cn.biiduu.biiduu.data.retrofit;

import android.accounts.NetworkErrorException;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.biiduu.biiduu.data.exception.AccountException;
import cn.biiduu.biiduu.util.LoggerUtil;
import retrofit2.adapter.rxjava.HttpException;

public abstract class DefaultSubscriber<T> extends rx.Subscriber<T> {
    public abstract void _onNext(T entity);

    @Override
    public void onNext(T entity) {
        _onNext(entity);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        String reason;
        if (e instanceof AccountException) {
            reason = "账户异常";
        } else if (e instanceof JsonSyntaxException) {
            reason = "数据格式化错误";
        } else if (e instanceof HttpException) {
            reason = "系统异常，请稍后重试";
        } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
            reason = "未连接网络或DNS错误";
        } else if (e instanceof NetworkErrorException) {
            reason = "网络错误";
        } else if (e instanceof SocketException || e instanceof SocketTimeoutException) {
            reason = "连接超时";
        } else {
            reason = "其他错误：" + e.getMessage();
        }
        LoggerUtil.e(reason);
    }
}
