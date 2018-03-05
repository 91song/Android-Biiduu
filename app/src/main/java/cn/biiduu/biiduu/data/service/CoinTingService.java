package cn.biiduu.biiduu.data.service;

import cn.biiduu.biiduu.constant.HttpConstants;
import cn.biiduu.biiduu.protocol.BaseEntity;
import cn.biiduu.biiduu.protocol.CoinTingDetailsProtocol;
import cn.biiduu.biiduu.protocol.CoinTingListProtocol;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface CoinTingService {
    @GET(HttpConstants.COIN_TING_DETAILS)
    Observable<BaseEntity<CoinTingDetailsProtocol>> getCoinTingDetails(@Query("id") String id);

    @GET(HttpConstants.COIN_TING_LIST)
    Observable<BaseEntity<CoinTingListProtocol>> listCoinTing(@Query("classify") String classify, @Query("offset") int offset, @Query("limit") int limit);

    @GET(HttpConstants.COIN_TING_VIEW)
    Observable<BaseEntity<CoinTingDetailsProtocol>> updateCoinTingPlayCount(@Query("id") String id);

    @POST(HttpConstants.COIN_TING_FEEDBACK)
    @FormUrlEncoded
    Observable<BaseEntity<CoinTingDetailsProtocol>> addCoinTingFeedback(@Field("id") String id, @Field("feedback") String feedback);
}
