package cn.biiduu.biiduu.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimerUtil {
    private static volatile TimerUtil instance;

    private TimerUtil() {

    }

    public static TimerUtil getInstance() {
        if (instance == null) {
            synchronized (TimerUtil.class) {
                if (instance == null) {
                    instance = new TimerUtil();
                }
            }
        }
        return instance;
    }

    public void setTimer(long delayTime, final TimerListener listener) {
        Observable.just("")
                .delay(delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (listener != null) {
                            listener.timeEnd();
                        }
                    }
                });

    }

    public interface TimerListener {
        void timeEnd();
    }
}
