package cn.biiduu.biiduu.data.repository;

import java.util.List;

import cn.biiduu.biiduu.data.retrofit.RetrofitHelper;
import cn.biiduu.biiduu.protocol.ArticleDetailsProtocol;
import cn.biiduu.biiduu.protocol.ArticleListProtocol;
import cn.biiduu.biiduu.protocol.ArticleProtocol;
import cn.biiduu.biiduu.protocol.CoinCircleDetailsProtocol;
import cn.biiduu.biiduu.protocol.CoinCircleListProtocol;
import cn.biiduu.biiduu.protocol.HostArticleProtocol;
import rx.Observable;

public class ArticleRepository extends BaseRepository {
    private static ArticleRepository instance;

    public static ArticleRepository getInstance() {
        if (instance == null) {
            synchronized (ArticleRepository.class) {
                if (instance == null) {
                    instance = new ArticleRepository();
                }
            }
        }
        return instance;
    }

    public Observable<ArticleDetailsProtocol> getArticleDetails(String articleId) {
        return transform(RetrofitHelper.getInstance().getArticleService().getArticleDetails(articleId));
    }

    public Observable<List<ArticleProtocol>> listTopArticle(String classify) {
        return transform(RetrofitHelper.getInstance().getArticleService().listTopArticle(classify));
    }

    public Observable<List<HostArticleProtocol>> listHostArticle(int limit) {
        return transform(RetrofitHelper.getInstance().getArticleService().listHostArticle(limit));
    }

    public Observable<ArticleListProtocol> listArticle(String classify, int offset, int limit, String publishTime) {
        return transform(RetrofitHelper.getInstance().getArticleService().listArticle(classify, offset, limit, publishTime, "0"));
    }

    public Observable<CoinCircleListProtocol> listCoinCircle(int page, int pageLen) {
        return transform(RetrofitHelper.getInstance().getArticleService().listCoinCircle(page, pageLen));
    }

    public Observable<CoinCircleDetailsProtocol> getCoinCircleDetails(String coinBannerId) {
        return transform(RetrofitHelper.getInstance().getArticleService().getCoinCircleDetails(coinBannerId));
    }

    public Observable<List<ArticleProtocol.ArticleBean>> listRecommendReadArticle(String articleId, int offset, int limit) {
        return transform(RetrofitHelper.getInstance().getArticleService().listRecommendReadArticle(articleId, offset, limit));
    }
}
