package cn.biiduu.biiduu.data.service;

import cn.biiduu.biiduu.constant.HttpConstants;
import cn.biiduu.biiduu.protocol.BaseEntity;
import cn.biiduu.biiduu.protocol.UpdateProtocol;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface AppService {
    @POST(HttpConstants.CHECK_NEW_VERSION)
    @FormUrlEncoded
    Observable<BaseEntity<UpdateProtocol>> checkUpdate(@Field("client") String client, @Field("nowVersion") String nowVersion);
}
