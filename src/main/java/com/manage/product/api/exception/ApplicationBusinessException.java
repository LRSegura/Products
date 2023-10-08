package com.manage.product.api.exception;

/**
 * RuntimeException used for specify all the business Exceptions.
 *
 * @author Luis
 */
public class ApplicationBusinessException extends RuntimeException {

    public ApplicationBusinessException(CharSequence genericMessage) {
        super(genericMessage.toString());
    }
}
