package cn.biiduu.biiduu.protocol;

public class CoinIndexTrendProtocol {
    private double coinValueSum;
    private long time;

    public double getCoinValueSum() {
        return coinValueSum;
    }

    public void setCoinValueSum(double coinValueSum) {
        this.coinValueSum = coinValueSum;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CoinIndexTrendProtocol{" +
                "coinValueSum=" + coinValueSum +
                ", time=" + time +
                '}';
    }
}
