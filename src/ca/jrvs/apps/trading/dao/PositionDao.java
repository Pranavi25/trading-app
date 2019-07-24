package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.activation.DataSource;
import java.util.List;

public class PositionDao {
    private static final Logger logger = LoggerFactory.getLogger(Accountdao.class);
    private final String Table_Name = "Position";
    private final String ID_column = "account_id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public PositionDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate( (javax.sql.DataSource) dataSource );
        this.simpleJdbcInsert = new SimpleJdbcInsert( (javax.sql.DataSource) dataSource ).withTableName(Table_Name).usingGeneratedKeyColumns(ID_column);
    }

    public List<Position> findByAccountId(Integer accountId){
        String selectSQL= "SELECT * FROM" + Table_Name + "WHERE account_id = ?";
        return (List<Position>) jdbcTemplate.queryForObject( selectSQL, BeanPropertyRowMapper.newInstance( Position.class ) );
    }

    public Long findByIdAndTicker(Integer accountId, String tickerId){
        String selectSQL = "SELECT position FROM " + Table_Name + "WHERE account_id = ?";
        Long position = 0L;
        position = jdbcTemplate.queryForObject( selectSQL,Long.class,accountId,tickerId );
        return position;
    }

}
