package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;

import java.util.List;

/**
 * Provides highest abstraction for service layer of the application.
 *
 * @param <T> DTO instance type
 * @param <K> id type
*/
public interface GenericService<T extends AbstractDTO, K> {

    /**
     * Creates <code>DTO</code> instance
     *
     * @param dto dto for creation
     * @return created dto
     */
    T create(T dto);

    /**
     * Gets <code>DTO</code> instance by <code>ID<code>
     *
     * @param id id for search
     * @return read dto
     */
    T read(K id);

    /**
     * Updates <code>DTO</code> instance
     *
     * @param dto dto for updating
     * @return updated dto
     */
    T update(T dto);

    /**
     * Deletes  <code>DTO</code> instance
     *
     * @param id id of the object to be deleted
     */
    void delete(K id);

    /**
     * Reads all <code>DTO</code> instances
     *
     * @return <code>DTO</code> instances list
     */
    List<T> readAll(Long pageNumber, Long amountOnPage);

    /**
     * Counts all entities
     *
     * @return entities amount
     */
    Long countAll();

    /**
     * Checks if this object exists
     * @param dto
     * @return true if object exists
     *          false if object doesn't exist
     */
    Boolean checkIfExists(T dto);

}
