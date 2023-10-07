package com.manage.product.api.rest.model.response;


import com.manage.product.api.rest.model.JsonData;

import java.util.Collection;
import java.util.List;

public record JsonDataResponse(Collection<? extends JsonData> data) implements JsonResponse {

    public JsonDataResponse(JsonData jsonData){
        this(List.of(jsonData));
    }
}
