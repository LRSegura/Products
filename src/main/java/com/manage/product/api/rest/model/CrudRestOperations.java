package com.manage.product.api.rest.model;

import java.util.List;

public interface CrudRestOperations<T extends JsonData> {
    List<? extends JsonData> restGetAll();
    T restGet(Long id);
    void restSave(T json);
    void restUpdate(T json);
    void restDelete(Long id);
}
