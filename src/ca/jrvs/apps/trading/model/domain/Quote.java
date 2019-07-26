package ca.jrvs.apps.trading.model.domain;

public class Quote implements Entity<String> {

    private Double askPrice;
    private Integer askSize;
    private Double bidPrize;
    private Integer bidSize;
    private Double lastPrice;
    private String ticker;

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Integer getAskSize() {
        return askSize;
    }

    public void setAskSize(Integer askSize) {
        this.askSize = askSize;
    }

    public Double getBidPrize() {
        return bidPrize;
    }

    public void setBidPrize(Double bidPrize) {
        this.bidPrize = bidPrize;
    }

    public Integer getBidSize() {
        return bidSize;
    }

    public void setBidSize(Integer bidSize) {
        this.bidSize = bidSize;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public String getId() {
        return this.ticker;
    }

    @Override
    public void setId(String id) {
        this.ticker=id;

    }
}
