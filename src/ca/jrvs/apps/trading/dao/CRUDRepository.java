package ca.jrvs.apps.trading.dao;

public interface CRUDRepository<E,ID> {
    /**
     * create a given entity
     * return the saved entity
     * @param entity must not be (@literal null)
     * @return the saved entity will never be (@literal null)
     * @throws IllegalArgumentException if entity is invalid
     * @throws java.sql.SQLException if the sql execution failed
     */
    E save(E entity);

    /**
     * Retrieves an entity by it's id
     * @param id must not be (@literal null)
     * @return the entity of found by id or null
     * @throws java.sql.SQLException if sql execution failed
     * @throws ResourceNotFoundExceptionDao if no entity is found
     */
    E findById(ID id);

    /**
     * Returns whether the entity by given id exists
     * @param id must not be (@literal null)
     * @return (@literal true) if id exists
     * else (@literal false)
     * @throws java.sql.SQLException if sql execution is failed
     * @throws ResourceNotFoundExceptionDao if no entity is found
     */
    boolean existsById(ID id);

    /**
     * Deletes by the given id
     * @param id must not be (@literal null)
     * @throws java.sql.SQLException if sql execution is failed
     * @throws IllegalArgumentException if the id is empty
     * @throws ResourceNotFoundExceptionDao if the entity is not found
     */
    void deleteById(ID id);


}
