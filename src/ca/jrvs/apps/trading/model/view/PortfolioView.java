package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;

import java.util.List;

public class PortfolioView {
    private List<SecurityRow> securityRows;

    public void setSecurityRows(List<SecurityRow> securityRows) {
        this.securityRows = securityRows;
    }

    public List<SecurityRow> getSecurityRows() { return securityRows;}

    public static class SecurityRow{
        private Position position;
        private Quote quote;
        private String ticket;


        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public Quote getQuote() {
            return quote;
        }

        public void setQuote(Quote quote) {
            this.quote = quote;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }
    }





}

