package cn.biiduu.biiduu.data.repository;

import java.util.List;

import cn.biiduu.biiduu.data.retrofit.RetrofitHelper;
import cn.biiduu.biiduu.protocol.CoinIndexProtocol;
import cn.biiduu.biiduu.protocol.CoinIndexTrendProtocol;
import rx.Observable;

public class CoinIndexRepository extends BaseRepository {
    private static CoinIndexRepository instance;

    public static CoinIndexRepository getInstance() {
        if (instance == null) {
            synchronized (CoinIndexRepository.class) {
                if (instance == null) {
                    instance = new CoinIndexRepository();
                }
            }
        }
        return instance;
    }

    public Observable<List<CoinIndexTrendProtocol>> listCoinIndexTrend(int endIndex, int type) {
        return transform(RetrofitHelper.getInstance().getCoinIndexService().listCoinIndexTrend(endIndex, type));
    }

    public Observable<CoinIndexTrendProtocol> getCoinIndexTrendNewest(int type) {
        return transform(RetrofitHelper.getInstance().getCoinIndexService().getCoinIndexTrendNewest(type));
    }

    public Observable<CoinIndexProtocol> getCoinIndex(int offset, int limit) {
        return transform(RetrofitHelper.getInstance().getCoinIndexService().getCoinIndex(offset, limit));
    }
}
