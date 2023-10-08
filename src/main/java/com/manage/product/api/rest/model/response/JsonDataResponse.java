package com.manage.product.api.rest.model.response;


import com.manage.product.api.rest.model.JsonData;

import java.util.Collection;
import java.util.List;

/**
 * Used to indicate a webservices response containing an element collection that will be transformed in Json Array.
 * See {@link com.manage.product.api.rest.model.JsonData}
 * @author Luis
 */
public record JsonDataResponse(Collection<? extends JsonData> data) implements JsonResponse {

    public JsonDataResponse(JsonData jsonData){
        this(List.of(jsonData));
    }
}
