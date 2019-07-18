package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbccrudDao<E extends Entity, ID> implements CRUDRepository<E, ID>{
    private static final Logger logger = LoggerFactory.getLogger( Accountdao.class );
    abstract public JdbcTemplate getJdbcTemplate();
    abstract public SimpleJdbcInsert getSimpleJdbcInsert();
    abstract  public String getTableName();
    abstract  public  String getIdName();
    abstract Class getEntityClass();

    @Override
    public E save(E entity){
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        Number newId = getSimpleJdbcInsert().executeAndReturnKey(sqlParameterSource);
        entity.setId(newId.intValue());
        return entity;
    }
    @Override
    public E findById(ID id){
        return findById(getIdName(),id,false,getEntityClass() );
    }

    public E findById(String idName, ID id, boolean forUpdate, Class clazz ){
        E t = null;
        String selectSQL = "Select * FROM " + getTableName() + "WHERE" + idName + "= ?";
        if(forUpdate){
            selectSQL += "for update";
        }
        logger.info(selectSQL);
        try {
            t = (E) getJdbcTemplate().queryForObject(selectSQL, BeanPropertyRowMapper.newInstance(clazz),id);
        } catch (EmptyResultDataAccessException e){
            logger.debug("Unable to find id" +id, e);
        }
        if(t == null){
            throw new ResourceNotFoundException("Unable to find the entity");
        }
        return t;
    }

    @Override
    public boolean existsById(ID id){
        return existsById( getIdName(),id );
    }

    public boolean existsById(String idName, ID id){
        if(id == null){
            throw new IllegalArgumentException("Unable to find the id");
        }
        String selectSQL = "SELECT COUNT(*) FROM" + getTableName() + "WHERE " + idName + "=? ";
        logger.info(selectSQL);
        Integer count = getJdbcTemplate().queryForObject( selectSQL,Integer.class, id );
        return count!= 0;
    }

    @Override
    public void deleteById(ID id){
         deleteById(getIdName(),id);
    }

    public void deleteById(String idName, ID id){
        if(id == null){
            throw new IllegalArgumentException("Unable to find the id" );
        }
        String deleteSQL = "DELETE FROM " + getTableName() + "WHERE " + idName + "=?";
        logger.info(deleteSQL);
        getJdbcTemplate().update(deleteSQL,id);
    }
}
