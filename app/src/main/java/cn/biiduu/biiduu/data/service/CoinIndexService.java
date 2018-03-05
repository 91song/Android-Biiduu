package cn.biiduu.biiduu.data.service;

import java.util.List;

import cn.biiduu.biiduu.constant.HttpConstants;
import cn.biiduu.biiduu.protocol.BaseEntity;
import cn.biiduu.biiduu.protocol.CoinIndexProtocol;
import cn.biiduu.biiduu.protocol.CoinIndexTrendProtocol;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface CoinIndexService {
    @GET(HttpConstants.COIN_INDEX_TREND)
    Observable<BaseEntity<List<CoinIndexTrendProtocol>>> listCoinIndexTrend(@Query("endIndex") int endIndex, @Query("type") int type);

    @GET(HttpConstants.COIN_INDEX_TREND_NEWEST)
    Observable<BaseEntity<CoinIndexTrendProtocol>> getCoinIndexTrendNewest(@Query("type") int type);

    @GET(HttpConstants.COIN_INDEX_LIST)
    Observable<BaseEntity<CoinIndexProtocol>> getCoinIndex(@Query("offset") int offset, @Query("limit") int limit);
}
