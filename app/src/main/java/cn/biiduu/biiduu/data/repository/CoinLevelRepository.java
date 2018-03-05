package cn.biiduu.biiduu.data.repository;

import cn.biiduu.biiduu.data.retrofit.RetrofitHelper;
import cn.biiduu.biiduu.protocol.CoinLevelDetailsProtocol;
import cn.biiduu.biiduu.protocol.CoinLevelListProtocol;
import cn.biiduu.biiduu.protocol.CoinLevelProtocol;
import rx.Observable;

public class CoinLevelRepository extends BaseRepository {
    private static CoinLevelRepository instance;

    public static CoinLevelRepository getInstance() {
        if (instance == null) {
            synchronized (CoinLevelRepository.class) {
                if (instance == null) {
                    instance = new CoinLevelRepository();
                }
            }
        }
        return instance;
    }

    public Observable<CoinLevelProtocol> listCoinLevelReplace() {
        return transform(RetrofitHelper.getInstance().getCoinLevelService().listCoinLevelReplace());
    }

    public Observable<CoinLevelListProtocol> listCoinLevel(int offset, int limit) {
        return transform(RetrofitHelper.getInstance().getCoinLevelService().listCoinLevel(offset, limit));
    }

    public Observable<CoinLevelDetailsProtocol> getCoinLevelDetails(String coinLevelId) {
        return transform(RetrofitHelper.getInstance().getCoinLevelService().getCoinLevelDetails(coinLevelId));
    }
}
