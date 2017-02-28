package by.bsu.rfct.fclearn.dao;

import by.bsu.rfct.fclearn.entity.AbstractEntity;
import java.util.List;

/**
 * Provides abstraction for DAO layer of the application.
 *
 * @param <T>  instance type
 * @param <K> primary key type
 */
public interface GenericDAO <T extends AbstractEntity, K> {

    /**
     * Inserts object <code>T</code> into database.
     *
     * @param entity object that will be inserted
     * @return primary key of the object in the database
     */
    K create(T entity);

    /**
     * Reads object <code>T</code> by key <code>K</code> from database.
     *
     * @param id primary key
     * @return found object
     */
    T read(K id);

    /**
     * Updates object in database.
     *
     * @param entity object that will be updated
     * @return primary key of the object in the database
     */
    K update(T entity);

    /**
     * Deletes object in database.
     *
     * @param id id of the object that will be deleted
     */
    void delete(K id);

    /**
     * Reads all objects from database.
     *
     * @return list of objects from database
     */
    List<T> readAll();

}
