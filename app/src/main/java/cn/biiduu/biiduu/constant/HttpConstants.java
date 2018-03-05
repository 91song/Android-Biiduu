package cn.biiduu.biiduu.constant;

public interface HttpConstants {
    /**
     * 文章详情
     */
    String ARTICLE_DETAILS = "article/get";
    /**
     * 首页Banner，资讯Banner
     */
    String ARTICLE_TOP_LIST = "article/top-list";

    /**
     * 首页最新资讯，资讯最新资讯
     */
    String ARTICLE_HOST_LIST = "article/hot-list";

    /**
     * 文章列表
     */
    String ARTICLE_LIST = "article/list";

    /**
     * 推荐阅读
     */
    String ARTICLE_RECOMMEND_READ_LIST = "article/get-tag-recommend-article";

    /**
     * 首页币级列表，币级币级列表
     */
    String COIN_LEVEL_REPLACE_LIST = "coinLevel/replace-list";

    /**
     * 投资评级
     */
    String COIN_LEVEL_LIST = "coinLevel/get";

    /**
     * 币级详情
     */
    String COIN_LEVEL_DETAILS = "coinLevel/coinLevel-details";

    /**
     * 币指趋势
     */
    String COIN_INDEX_TREND = "coin/coinType-trend";

    /**
     * 币都指数
     */
    String COIN_INDEX_TREND_NEWEST = "coin/coinType-trend-newest";

    /**
     * 币指采集样本
     */
    String COIN_INDEX_LIST = "coin/coinindex-list-page";

    /**
     * 币圈列表
     */
    String ARTICLE_COIN_CIRCLE_LIST = "coin/coinBanner-list";

    /**
     * 币圈详情
     */
    String ARTICLE_COIN_CIRCLE_DETAILS = "coin/coinBanner-detail";

    /**
     * 币Ting反馈
     */
    String COIN_TING_FEEDBACK = "ting/feedback";

    /**
     * 币Ting列表
     */
    String COIN_TING_LIST = "ting/list";

    /**
     * 币Ting详情
     */
    String COIN_TING_DETAILS = "ting/get";

    /**
     * 币Ting播放次数
     */
    String COIN_TING_VIEW = "ting/view";

    /**
     * 检查新版本
     */
    String CHECK_NEW_VERSION = "manager/version/check";
}
