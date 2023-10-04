package com.manage.product.api.rest.model;

import java.util.List;

public interface CrudRestOperations<T extends JsonData> {
    List<? extends JsonData> restGet();
    void restSave(T json);
    void restUpdate(T json);
    void restDelete(Long id);
}
