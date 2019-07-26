package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.Autowired;
import ca.jrvs.apps.trading.dao.MarketDatadao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuoteService {
    private QuoteDao quoteDao;
    private MarketDatadao marketDatadao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDatadao marketDatadao){
        this.quoteDao = quoteDao;
        this.marketDatadao = marketDatadao;
    }

    public static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
        Quote quote = new Quote();
        quote.setTicker( iexQuote.getSymbol() );
        quote.setLastPrice( Double.parseDouble( Optional.ofNullable( iexQuote.getLatestPrice() ).orElse( "0" ) ) );
        quote.setAskPrice( Double.parseDouble( Optional.ofNullable( iexQuote.getIexAskPrice() ).orElse( "0" ) ) );
        quote.setBidPrize( Optional.ofNullable( iexQuote.getIexBidPrize() ).orElse( Double.valueOf( "0" ) ) );
        quote.setBidSize( Integer.parseInt( Optional.ofNullable( iexQuote.getIexBidSize() ).orElse( "0" ) ) );
        quote.setAskSize( Integer.parseInt( Optional.of( iexQuote.getIexAskSize() ).orElse( "0" ) ) );
        return quote;
    }

    public void initQuote(List<String> tickers){
        List<IexQuote> securityList = new ArrayList<>();
        MarketDatadao marketDatadao1 = marketDatadao;
        for (String ticker : tickers) {
            IexQuote iexQuoteByTicker = null;
            try {
                iexQuoteByTicker = marketDatadao1.findIexQuoteByTicker( ticker );
            } catch (IOException e) {
                e.printStackTrace();
            }
            securityList.add( iexQuoteByTicker );
        }
        List<Quote>  quotes = securityList.stream().map(QuoteService::buildQuoteFromIexQuote).collect( Collectors.toList());
        quotes.forEach(quote -> {
            if(!quoteDao.existsById( quote.getId() )){
                quoteDao.save(quote);
            }
        });
    }

    public void updateMarketData(){
        List<Quote> quotes = quoteDao.findAll();
        List<String> tickers = quotes.stream().map(Quote::getTicker).collect(Collectors.toList() );
        List<IexQuote> iexQuotes = marketDatadao.findIexQuoteByTicker( tickers );
        List<Quote> updateQuotes = iexQuotes.stream().map(QuoteService::buildQuoteFromIexQuote).collect( Collectors.toList());
        updateQuotes.forEach( quote -> {
            quoteDao.update(quote);
        } );

        }
}
