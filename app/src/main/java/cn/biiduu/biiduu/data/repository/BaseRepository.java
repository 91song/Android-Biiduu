package cn.biiduu.biiduu.data.repository;

import android.accounts.NetworkErrorException;

import cn.biiduu.biiduu.data.exception.DefaultErrorException;
import cn.biiduu.biiduu.protocol.BaseEntity;
import cn.biiduu.biiduu.protocol.BaseProtocol;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseRepository {
    <T> Observable<T> transform(Observable<BaseEntity<T>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    if (result == null) {
                        return Observable.error(new NetworkErrorException());
                    } else if (result.success && result.code.equals("200")) {
                        return Observable.just(result.data);
                    } else {
                        return Observable.error(new DefaultErrorException(result.code, result.message));
                    }
                });
    }

    protected <T> Observable<T> transformNew(Observable<BaseProtocol<T>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    if (result == null) {
                        return Observable.error(new NetworkErrorException());
                    } else if (result.code == 0) {
                        return Observable.just(result.data);
                    } else {
                        return Observable.error(new DefaultErrorException(String.valueOf(result.code), result.msg));
                    }
                });
    }
}
