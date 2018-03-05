package cn.biiduu.biiduu.protocol;

import java.util.List;

public class CoinIndexProtocol {
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
        private String code;
        private String coinType;
        private String coinValue;
        private double price;
        private String circulateQuantity;
        private String volume24h;
        private String fluctuation24;
        private String pricetrend24;
        private String pricetrend7Day;
        private long scheduleTime;
        private String coinDataSource;
        private int lastest;
        private double weight;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCoinType() {
            return coinType;
        }

        public void setCoinType(String coinType) {
            this.coinType = coinType;
        }

        public String getCoinValue() {
            return coinValue;
        }

        public void setCoinValue(String coinValue) {
            this.coinValue = coinValue;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCirculateQuantity() {
            return circulateQuantity;
        }

        public void setCirculateQuantity(String circulateQuantity) {
            this.circulateQuantity = circulateQuantity;
        }

        public String getVolume24h() {
            return volume24h;
        }

        public void setVolume24h(String volume24h) {
            this.volume24h = volume24h;
        }

        public String getFluctuation24() {
            return fluctuation24;
        }

        public void setFluctuation24(String fluctuation24) {
            this.fluctuation24 = fluctuation24;
        }

        public String getPricetrend24() {
            return pricetrend24;
        }

        public void setPricetrend24(String pricetrend24) {
            this.pricetrend24 = pricetrend24;
        }

        public String getPricetrend7Day() {
            return pricetrend7Day;
        }

        public void setPricetrend7Day(String pricetrend7Day) {
            this.pricetrend7Day = pricetrend7Day;
        }

        public long getScheduleTime() {
            return scheduleTime;
        }

        public void setScheduleTime(long scheduleTime) {
            this.scheduleTime = scheduleTime;
        }

        public String getCoinDataSource() {
            return coinDataSource;
        }

        public void setCoinDataSource(String coinDataSource) {
            this.coinDataSource = coinDataSource;
        }

        public int getLastest() {
            return lastest;
        }

        public void setLastest(int lastest) {
            this.lastest = lastest;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    @Override
    public String toString() {
        return "CoinIndexProtocol{" +
                "total=" + total +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", pageLength=" + pageLength +
                ", rows=" + rows +
                '}';
    }
}
