package ca.jrvs.apps.trading;


import ca.jrvs.apps.trading.dao.Autowired;
import ca.jrvs.apps.trading.dao.MarketDatadao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.util.StringUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;

@Configuration
public class AppConfig {
    private Logger logger = LoggerFactory.getLogger( MarketDatadao.class );

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 50
        cm.setMaxTotal( 50 );
        // Increase default max connection per route to 50
        cm.setDefaultMaxPerRoute( 50 );
        return (HttpClientConnectionManager) cm;
    }

    //@Value("https://cloud.iexapis.com/v1/")
    //private String iexhost;
    //private String localhost;

    @Bean
    public MarketDataConfig marketDataConfig() {

        /**
         * checking if the IEX pub token and iexhost which are environment and property are empty
         * @throws IllegalArgumentException if they are empty
         */
        //if (!StringUtil.isEmpty( System.getenv( "IEX_PUB_TOKEN" ) ) ) {
          //  throw new IllegalArgumentException( "Environment: IEX_PUB_TOKEN or Property: iexhost are not set properly" );
        //}
        /**
         * setting the token and host objects
         */
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        //marketDataConfig.setToken( System.getenv( "IEX_PUB_TOKEN" ) );
        marketDataConfig.setToken( "pk_8dd910eba18b4dd18d024d754e4c7448" );
        marketDataConfig.setHost( "https://cloud.iexapis.com/stable/" );
        return marketDataConfig;

    }

    /*@Bean
    public PlatformTransactionManager txManager(DataSource datasource) {
        return new DataSourceTransactionManager( datasource );
    }*/

    @Bean
    public DataSource dataSource() {
        String jdbcurl;
        String user_name;
        String password;

        /**
         * checking if the IEX pub token and iexhost which are environment and property are empty
         * @throws IllegalArgumentException if jdbc is missing
         *
         */
        jdbcurl = System.getenv( "PSQL_URL" );
        user_name = System.getenv( "PSQL_USERNAME" );
        password = System.getenv( "PSQL_PASSWORD" );

        logger.error( "JDBC:" + jdbcurl );
        if (StringUtil.isEmpty( jdbcurl, user_name, password )) {
            throw new IllegalArgumentException( "The credentials to connect with jdbc are missing" );
        }

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName( "org.postgresql.Driver" );
        basicDataSource.setUrl( jdbcurl );
        basicDataSource.setUsername( user_name );
        basicDataSource.setPassword( password );
        return basicDataSource;
    }


}
