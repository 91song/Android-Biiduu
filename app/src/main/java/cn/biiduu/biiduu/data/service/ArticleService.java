package cn.biiduu.biiduu.data.service;

import java.util.List;

import cn.biiduu.biiduu.constant.HttpConstants;
import cn.biiduu.biiduu.protocol.ArticleDetailsProtocol;
import cn.biiduu.biiduu.protocol.ArticleListProtocol;
import cn.biiduu.biiduu.protocol.ArticleProtocol;
import cn.biiduu.biiduu.protocol.BaseEntity;
import cn.biiduu.biiduu.protocol.CoinCircleDetailsProtocol;
import cn.biiduu.biiduu.protocol.CoinCircleListProtocol;
import cn.biiduu.biiduu.protocol.HostArticleProtocol;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ArticleService {
    @POST(HttpConstants.ARTICLE_DETAILS)
    @FormUrlEncoded
    Observable<BaseEntity<ArticleDetailsProtocol>> getArticleDetails(@Field("articleId") String articleId);

    @POST(HttpConstants.ARTICLE_TOP_LIST)
    @FormUrlEncoded
    Observable<BaseEntity<List<ArticleProtocol>>> listTopArticle(@Field("classify") String classify);

    @POST(HttpConstants.ARTICLE_HOST_LIST)
    @FormUrlEncoded
    Observable<BaseEntity<List<HostArticleProtocol>>> listHostArticle(@Field("limit") int limit);

    @POST(HttpConstants.ARTICLE_LIST)
    @FormUrlEncoded
    Observable<BaseEntity<ArticleListProtocol>> listArticle(@Field("classify") String classify, @Field("offset") int offset, @Field("limit") int limit, @Field("publishTime") String publishTime, @Field("ignoreTop") String ignoreTop);

    @GET(HttpConstants.ARTICLE_COIN_CIRCLE_LIST)
    Observable<BaseEntity<CoinCircleListProtocol>> listCoinCircle(@Query("page") int page, @Query("pageLen") int pageLen);

    @GET(HttpConstants.ARTICLE_COIN_CIRCLE_DETAILS)
    Observable<BaseEntity<CoinCircleDetailsProtocol>> getCoinCircleDetails(@Query("coinBannerId") String coinBannerId);

    @POST(HttpConstants.ARTICLE_RECOMMEND_READ_LIST)
    @FormUrlEncoded
    Observable<BaseEntity<List<ArticleProtocol.ArticleBean>>> listRecommendReadArticle(@Field("articleId") String articleId, @Field("offset") int offset, @Field("limit") int limit);
}
