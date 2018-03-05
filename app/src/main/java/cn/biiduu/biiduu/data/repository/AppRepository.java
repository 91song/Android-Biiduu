package cn.biiduu.biiduu.data.repository;

import cn.biiduu.biiduu.data.retrofit.RetrofitHelper;
import cn.biiduu.biiduu.protocol.UpdateProtocol;
import rx.Observable;

public class AppRepository extends BaseRepository {
    private static AppRepository instance;

    public static AppRepository getInstance() {
        if (instance == null) {
            synchronized (AppRepository.class) {
                if (instance == null) {
                    instance = new AppRepository();
                }
            }
        }
        return instance;
    }

    public Observable<UpdateProtocol> checkUpdate(String client, String nowVersion) {
        return transform(RetrofitHelper.getInstance().getAppService().checkUpdate(client, nowVersion));
    }
}
