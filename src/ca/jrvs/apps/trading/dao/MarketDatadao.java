package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ca.jrvs.apps.trading.util.JsonUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Ticker;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;

public class MarketDatadao {
    private LoggerFactory _Factory;
    private HttpClientConnectionManager httpClientConnectionManager;
    private final String BATCH_URL;
    private Logger logger = _Factory.getLogger(MarketDatadao.class);

    @Autowired
    public MarketDatadao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        BATCH_URL = marketDataConfig.getHost() + "/stock/market/batch?symbols=%s&types=quote&token=" + marketDataConfig.getToken();
    }

    public List<IexQuote> findIexQuoteByTicker(List<String> tickersList)  {
        // Get the uri
        //convert the list into a comma separated string
        String tickers = Joiner.on(",").join(tickersList);
        String uri = String.format(BATCH_URL,tickers);
        logger.info("Get URI" + uri);
        //Get Http response body in string
        String response = executeHttpGet(uri);
        JSONObject IexQuotesJson = new JSONObject(response);
        //Iex will skip invalid symbols/ticker..we need to check it
        if (IexQuotesJson.length() != tickersList.size()) {
            throw new IllegalArgumentException("Invalid ticker/symbol");
        }

        //Unmarshal JSON object
        List<IexQuote> iexQuotes = new ArrayList<>();
        IexQuotesJson.keys().forEachRemaining(ticker -> {
            try{
                String quoteStr = ((JSONObject) IexQuotesJson.get(ticker)).get("quote").toString();
                IexQuote iexQuote = JsonUtil.toObjectFromJson(quoteStr,IexQuote.class);
                iexQuotes.add(iexQuote);
            }catch(IOException e){
                throw new DataRetrievalFailureException((String) IexQuotesJson.get(ticker),e);
            }
        });
        return iexQuotes;
    }

    protected String executeHttpGet(String url)  {
        try(CloseableHttpClient httpClient = getHttpClient()){
            HttpGet httpGet = new HttpGet(url);
         try(CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet)){
             switch(response.getStatusLine().getStatusCode()){
                 case 200: //error 200
                     //EntityUtils.toString will close inputstream in entity
                     String body = EntityUtils.toString(response.getEntity());
                     return Optional.ofNullable(body).orElseThrow(() -> new IOException("Unexpected empty Http body"));
                 case 400: //error 400
                     throw new ResourceNotFoundException("Not found");
                 default:
                     throw new DataRetrievalFailureException("unexpected status" + response.getStatusLine().getStatusCode());
             }
         }
        }catch (IOException e) {
            throw new DataRetrievalFailureException("Http execution error",e);
        }
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(httpClientConnectionManager).setConnectionManagerShared(true).build();
    }



    public IexQuote findIexQuoteByTicker(String ticker) throws IOException {
        List<IexQuote> quotes = findIexQuoteByTicker(Arrays.asList(ticker));
        return quotes.get(0);

    }


}
