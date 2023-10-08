package com.manage.product.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to inject the current date to the applied field. This annotation must be used in a hibernate entity
 * field. The date is injected by the
 * {@link com.manage.product.api.persistence.validation.HibernateEventHandlers} listener. The injection moment
 * depends on {@link com.manage.product.api.annotation.InjectedDateType} value passed to the variable dateType.
 * @author Luis
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectedDate {
    InjectedDateType dateType() default InjectedDateType.REGISTER_DATE;
}
