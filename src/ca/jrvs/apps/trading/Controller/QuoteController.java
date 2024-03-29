package ca.jrvs.apps.trading.Controller;

import ca.jrvs.apps.trading.dao.MarketDatadao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/quote")
/**
 * Controller binds path and methods together
 */
public class QuoteController {

    /**
     * Dependencies are quote service
     * quoteDao
     * marketdao
     */

    private QuoteService quoteService;
    private QuoteDao quoteDao;
    private MarketDatadao marketDatadao;

    /**
     * Constructor
     * @param quoteService
     * @param quoteDao
     * @param marketDataDao
     */
    @Autowired
    public QuoteController(QuoteService quoteService, QuoteDao quoteDao,
                           MarketDatadao marketDataDao) {
        this.quoteService = quoteService;
        this.quoteDao = quoteDao;
        this.marketDatadao = marketDatadao;
    }

    /**
     * For all Http Request the path is mapped to the below method
     */
    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    public void updateMarketData() {
        try {
            quoteService.updateMarketData();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public void putQuote(@RequestBody Quote quote) {
        try {
            quoteDao.update(quote);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }


    @PostMapping(path = "/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuote(@PathVariable String tickerId) {
        try {
            quoteService.initQuote( Collections.singletonList( tickerId ) );
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }


    @GetMapping(path = "/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList() {
        try {
            return quoteDao.findAll();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
    /**
     *
     * @param
     * @return object with @PathVariable
     */
    @GetMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker) {
        try {
            return marketDatadao.findIexQuoteByTicker(ticker);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
