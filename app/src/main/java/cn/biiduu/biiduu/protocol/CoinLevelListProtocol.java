package cn.biiduu.biiduu.protocol;

import java.util.List;

public class CoinLevelListProtocol {
    private int total;
    private int totalPage;
    private int page;
    private int pageLength;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageLength() {
        return pageLength;
    }

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String id;
        private String englishName;
        private String chineseName;
        private String coinTypeCode;
        private String logoUrl;
        private double newScore;
        private Object gradingInstitution;
        private String simpleEvaluate;
        private String location;
        private String offTheShelf;
        private String fluctuation24;
        private double price;
        private String content;
        private Object newsInformation;
        private long publishTime;
        private String scoreStatus;
        private String contentUrl;

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

        public Object getGradingInstitution() {
            return gradingInstitution;
        }

        public void setGradingInstitution(Object gradingInstitution) {
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

        public Object getNewsInformation() {
            return newsInformation;
        }

        public void setNewsInformation(Object newsInformation) {
            this.newsInformation = newsInformation;
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
    }

    @Override
    public String toString() {
        return "CoinLevelListProtocol{" +
                "total=" + total +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", pageLength=" + pageLength +
                ", rows=" + rows +
                '}';
    }
}
