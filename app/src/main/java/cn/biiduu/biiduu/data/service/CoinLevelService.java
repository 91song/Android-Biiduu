package cn.biiduu.biiduu.data.service;

import cn.biiduu.biiduu.constant.HttpConstants;
import cn.biiduu.biiduu.protocol.BaseEntity;
import cn.biiduu.biiduu.protocol.CoinLevelDetailsProtocol;
import cn.biiduu.biiduu.protocol.CoinLevelListProtocol;
import cn.biiduu.biiduu.protocol.CoinLevelProtocol;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface CoinLevelService {
    @POST(HttpConstants.COIN_LEVEL_REPLACE_LIST)
    Observable<BaseEntity<CoinLevelProtocol>> listCoinLevelReplace();

    @POST(HttpConstants.COIN_LEVEL_LIST)
    @FormUrlEncoded
    Observable<BaseEntity<CoinLevelListProtocol>> listCoinLevel(@Field("offset") int offset, @Field("limit") int limit);

    @POST(HttpConstants.COIN_LEVEL_DETAILS)
    @FormUrlEncoded
    Observable<BaseEntity<CoinLevelDetailsProtocol>> getCoinLevelDetails(@Field("coinLevelId") String coinLevelId);
}
