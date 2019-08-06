package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class Accountdao extends JdbccrudDao<Account, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(Accountdao.class);
    private final String Table_Name = "Account";
    private final String ID_column = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public Accountdao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate( (javax.sql.DataSource) dataSource );
        this.simpleJdbcInsert = new SimpleJdbcInsert( (javax.sql.DataSource) dataSource ).withTableName(Table_Name).usingGeneratedKeyColumns(ID_column);
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
        return Table_Name;
    }

    @Override
    public String getIdName() {
        return ID_column;
    }

    @Override
    Class getEntityClass() {
        return Accountdao.class;
    }
    public Account findByTraderId(Integer traderId) {
        return super.findById("trader_id", traderId, false, getEntityClass());
    }


    public Account findByTraderIdForUpdate(Integer traderId) {
        return super.findById("trader_id", traderId, true, getEntityClass());
    }

    public Account updateAmountById(Integer id, Double amount) {
        if (super.existsById(id)) {
            String sql = "UPDATE " + Table_Name + " SET amount = ? WHERE id = ?";
            int row = jdbcTemplate.update(sql, amount, id);
            logger.debug("Update amount row=" + row);
            if (row != 1) {
                throw new IncorrectResultSizeDataAccessException(1, row);
            }
            return findById(id);
        }
        return null;
    }
}
