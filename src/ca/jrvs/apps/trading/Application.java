package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.dao.Autowired;
import ca.jrvs.apps.trading.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;



@SpringBootApplication()
public class Application implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger( Application.class );

    @Autowired
    private DataSource dataSource;

    @Autowired
    private QuoteService quoteService;

    @Value("aapl,msft,amzn,fb,alibaba")
    private String[] initList;

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class,args);
        }

    @Override
    public void run(String... args) throws Exception {

    }
}

