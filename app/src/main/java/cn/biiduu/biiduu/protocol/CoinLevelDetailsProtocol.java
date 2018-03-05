package cn.biiduu.biiduu.protocol;

import java.util.List;

public class CoinLevelDetailsProtocol {
    private String id;
    private String englishName;
    private String chineseName;
    private String coinTypeCode;
    private String logoUrl;
    private double newScore;
    private String gradingInstitution;
    private String simpleEvaluate;
    private String location;
    private String offTheShelf;
    private String fluctuation24;
    private double price;
    private String content;
    private long publishTime;
    private String scoreStatus;
    private String contentUrl;
    private List<NewsInformationBean> newsInformation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getCoinTypeCode() {
        return coinTypeCode;
    }

    public void setCoinTypeCode(String coinTypeCode) {
        this.coinTypeCode = coinTypeCode;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public double getNewScore() {
        return newScore;
    }

    public void setNewScore(double newScore) {
        this.newScore = newScore;
    }

    public String getGradingInstitution() {
        return gradingInstitution;
    }

    public void setGradingInstitution(String gradingInstitution) {
        this.gradingInstitution = gradingInstitution;
    }

    public String getSimpleEvaluate() {
        return simpleEvaluate;
    }

    public void setSimpleEvaluate(String simpleEvaluate) {
        this.simpleEvaluate = simpleEvaluate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOffTheShelf() {
        return offTheShelf;
    }

    public void setOffTheShelf(String offTheShelf) {
        this.offTheShelf = offTheShelf;
    }

    public String getFluctuation24() {
        return fluctuation24;
    }

    public void setFluctuation24(String fluctuation24) {
        this.fluctuation24 = fluctuation24;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getScoreStatus() {
        return scoreStatus;
    }

    public void setScoreStatus(String scoreStatus) {
        this.scoreStatus = scoreStatus;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public List<NewsInformationBean> getNewsInformation() {
        return newsInformation;
    }

    public void setNewsInformation(List<NewsInformationBean> newsInformation) {
        this.newsInformation = newsInformation;
    }

    public static class NewsInformationBean {
        private Object newsId;
        private String coinLevelId;
        private String title;
        private String source;
        private String url;

        public Object getNewsId() {
            return newsId;
        }

        public void setNewsId(Object newsId) {
            this.newsId = newsId;
        }

        public String getCoinLevelId() {
            return coinLevelId;
        }

        public void setCoinLevelId(String coinLevelId) {
            this.coinLevelId = coinLevelId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "CoinLevelDetailsProtocol{" +
                "id='" + id + '\'' +
                ", englishName='" + englishName + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", coinTypeCode='" + coinTypeCode + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", newScore=" + newScore +
                ", gradingInstitution='" + gradingInstitution + '\'' +
                ", simpleEvaluate='" + simpleEvaluate + '\'' +
                ", location='" + location + '\'' +
                ", offTheShelf='" + offTheShelf + '\'' +
                ", fluctuation24='" + fluctuation24 + '\'' +
                ", price=" + price +
                ", content='" + content + '\'' +
                ", publishTime=" + publishTime +
                ", scoreStatus='" + scoreStatus + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", newsInformation=" + newsInformation +
                '}';
    }
}
