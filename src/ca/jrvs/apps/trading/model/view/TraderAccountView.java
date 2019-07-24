package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Trader;

import java.util.List;

public class TraderAccountView {
    private List<account> account;
    private List<trader> trader;

    public void setAccount(List<account> accountList){ this.account = account;}
    public List<account> getAccount(){ return account;}

    public void setTrader(Trader traderList){this.trader = trader;}
    public List<trader> getTrader(){return trader;}


    public class account{
        private Double account;
        private Integer id;
        private Integer traderId;

        public Double getAccount() {
            return account;
        }

        public void setAccount(Double account) {
            this.account = account;
        }

        public Integer getId() {
            return id;
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

    public class trader{
        private String country;
        private String dob;
        private String email;
        private String firstName;
        private Integer id;
        private String  lastName;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
