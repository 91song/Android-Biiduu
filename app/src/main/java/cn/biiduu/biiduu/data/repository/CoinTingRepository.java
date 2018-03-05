package cn.biiduu.biiduu.data.repository;

import cn.biiduu.biiduu.data.retrofit.RetrofitHelper;
import cn.biiduu.biiduu.protocol.CoinTingDetailsProtocol;
import cn.biiduu.biiduu.protocol.CoinTingListProtocol;
import rx.Observable;

public class CoinTingRepository extends BaseRepository {
    private static CoinTingRepository instance;

    public static CoinTingRepository getInstance() {
        if (instance == null) {
            synchronized (CoinTingRepository.class) {
                if (instance == null) {
                    instance = new CoinTingRepository();
                }
            }
        }
        return instance;
    }

    public Observable<CoinTingListProtocol> listCoinTing(String classify, int offset, int limit) {
        return transform(RetrofitHelper.getInstance().getCoinTingService().listCoinTing(classify, offset, limit));
    }

    public Observable<CoinTingDetailsProtocol> getCoinTingDetails(String id) {
        return transform(RetrofitHelper.getInstance().getCoinTingService().getCoinTingDetails(id));
    }

    public Observable<CoinTingDetailsProtocol> updateCoinTingPlayCount(String id) {
        return transform(RetrofitHelper.getInstance().getCoinTingService().updateCoinTingPlayCount(id));
    }

    public Observable<CoinTingDetailsProtocol> addCoinTingFeedback(String id, String feedback) {
        return transform(RetrofitHelper.getInstance().getCoinTingService().addCoinTingFeedback(id, feedback));
    }
}
