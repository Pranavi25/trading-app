package ca.jrvs.apps.trading.model.domain;

public class Account implements Entity{
    private Double amount;
    private Integer id;
    private Integer traderId;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Object o) {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

}
