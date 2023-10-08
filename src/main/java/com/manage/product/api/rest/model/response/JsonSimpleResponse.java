package com.manage.product.api.rest.model.response;

/**
 * Used to indicate a webservices response containing a simple message.
 * @author Luis
 */
public record JsonSimpleResponse(String message) implements JsonResponse {
}
