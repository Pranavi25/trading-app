package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketorderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public class SecurityOrderDao implements CRUDRepository<SecurityOrder,Integer> {
    /**
     * Dependencies
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);
    private final static String TABLE_NAME = "security_order";
    private final static String ID_NAME = "account_id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    /**
     * Constructor
     *
     */
    @Autowired
    public SecurityOrderDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate( dataSource );
        simpleJdbcInsert =  new SimpleJdbcInsert( dataSource ).withTableName( TABLE_NAME );
    }

    public SecurityOrder save(SecurityOrder entity) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        Number newId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        //entity.setSize(newId.intValue());
        entity.setId( newId.intValue() );
        return entity;
    }



    @Override
    public SecurityOrder findById(Integer id) {
        SecurityOrder securityOrder = null;
        securityOrder = jdbcTemplate.queryForObject("SELECT * FROM" + TABLE_NAME + "WHERE id = ?", BeanPropertyRowMapper.newInstance( SecurityOrder.class ),id );
        return securityOrder;
    }

    @Override
    public boolean existsById(Integer id) {
        String selectSQL = "SELECT COUNT(*) FROM" + TABLE_NAME + "WHERE " + id + "=? ";
        logger.info(selectSQL);
        Integer count = jdbcTemplate.queryForObject( selectSQL,Integer.class, id );
        return count!= 0;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + "WHERE " + id + "=?";
        logger.info(deleteSQL);
        jdbcTemplate.update(deleteSQL,id);

    }

}
