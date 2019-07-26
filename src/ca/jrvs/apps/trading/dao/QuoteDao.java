package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.util.StringUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuoteDao extends JdbccrudDao<Quote,String> {

    /**
     * Dependencies
     *
     */
    private final static String TABLE_NAME = "quote";
    private final static String ID_NAME = "ticker";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    /**
     * Constructor
     *
     */
    @Autowired
    public QuoteDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate( dataSource );
        simpleJdbcInsert =  new SimpleJdbcInsert( dataSource ).withTableName( TABLE_NAME );
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return ID_NAME;
    }

    @Override
    Class getEntityClass() {
        return Quote.class;
    }

    @Override
    public Quote save(Quote quote){
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource( quote );
        int row = getSimpleJdbcInsert().execute(sqlParameterSource);
        return quote;
    }

    //@Override
    public List<Quote> findAll(){
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(selectSQL, BeanPropertyRowMapper.newInstance( Quote.class ) );
    }

    //@Override
    public void update(Quote quotes){
        String updateSQL = "UPDATE quote SET  last_price =? , bid_price = ?, bid_size = ? , ask_price = ?, ask_size = ? WHERE ticker = ? ";
        jdbcTemplate.update(updateSQL,quotes.getLastPrice(),quotes.getBidPrize(),quotes.getBidSize(),quotes.getAskPrice(),quotes.getAskSize(),quotes.getTicker());

    }
}
