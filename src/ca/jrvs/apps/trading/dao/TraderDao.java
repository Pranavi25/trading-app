package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.activation.DataSource;

public class TraderDao implements CRUDRepository<Trader, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);
    private final String Table_Name = "trader";
    private final String ID_column = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public TraderDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate( (javax.sql.DataSource) dataSource );
        this.simpleJdbcInsert = new SimpleJdbcInsert( (javax.sql.DataSource) dataSource ).withTableName(Table_Name).usingGeneratedKeyColumns(ID_column);
    }

    @Override
    public Trader save(Trader entity) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        Number newId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        entity.setId(newId.intValue());
        return entity;
    }

    @Override
    public Trader findById(Integer id) {
        Trader trader = null;
        trader = jdbcTemplate.queryForObject("SELECT * FROM" + Table_Name + "WHERE id = ?", BeanPropertyRowMapper.newInstance( Trader.class ),id );
        return trader;
    }

    @Override
    public boolean existsById(Integer id) {
        String selectSQL = "SELECT COUNT(*) FROM" + Table_Name + "WHERE " + id + "=? ";
        logger.info(selectSQL);
        Integer count = jdbcTemplate.queryForObject( selectSQL,Integer.class, id );
        return count!= 0;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteSQL = "DELETE FROM " + Table_Name + "WHERE " + id + "=?";
        logger.info(deleteSQL);
        jdbcTemplate.update(deleteSQL,id);

    }
}
