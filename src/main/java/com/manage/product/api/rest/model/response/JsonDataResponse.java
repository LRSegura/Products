package com.manage.product.api.rest.model.response;


import com.manage.product.api.rest.model.JsonData;

import java.util.Collection;

public record JsonDataResponse(Collection<? extends JsonData> data) implements JsonResponse {
}
