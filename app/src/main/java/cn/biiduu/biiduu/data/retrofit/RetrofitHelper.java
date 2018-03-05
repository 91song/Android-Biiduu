package cn.biiduu.biiduu.data.retrofit;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cn.biiduu.biiduu.BuildConfig;
import cn.biiduu.biiduu.data.service.AppService;
import cn.biiduu.biiduu.data.service.ArticleService;
import cn.biiduu.biiduu.data.service.CoinLevelService;
import cn.biiduu.biiduu.data.service.CoinIndexService;
import cn.biiduu.biiduu.data.service.CoinTingService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static volatile Retrofit retrofit;
    private static volatile RetrofitHelper instance;

    private RetrofitHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new CustomHttpLogger());
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        OkHttpClient client = builder.retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    private static <T> T create(Class<T> apiService) {
        return retrofit.create(apiService);
    }

    public ArticleService getArticleService() {
        return create(ArticleService.class);
    }

    public CoinLevelService getCoinLevelService() {
        return create(CoinLevelService.class);
    }

    public CoinIndexService getCoinIndexService() {
        return create(CoinIndexService.class);
    }

    public CoinTingService getCoinTingService() {
        return create(CoinTingService.class);
    }

    public AppService getAppService() {
        return create(AppService.class);
    }
}