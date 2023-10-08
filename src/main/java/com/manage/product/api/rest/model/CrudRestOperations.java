package com.manage.product.api.rest.model;

import java.util.List;

/**
 * This interface in an abstraction for CRUD rest operation. This should be implemented for a service class to
 * implement the business code for CRUD operation. See {@link com.manage.product.api.rest.model.JsonData}
 *
 * @author Luis
 */
public interface CrudRestOperations<T extends JsonData> {
    /**
     * Represent the business logic to get all entities.
     */
    List<? extends JsonData> restGetAll();

    /**
     * Represent the business logic to get an entity by id.
     */
    T restGet(Long id);

    /**
     * Represent the business logic to save an entity.
     */
    void restSave(T json);

    /**
     * Represent the business logic to update an entity.
     */
    void restUpdate(T json);

    /**
     * Represent the business logic to delete an entity.
     */
    void restDelete(Long id);
}
