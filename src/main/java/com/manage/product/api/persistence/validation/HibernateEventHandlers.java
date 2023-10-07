package com.manage.product.api.persistence.validation;



import com.manage.product.api.annotation.InjectedDate;
import com.manage.product.api.annotation.InjectedDateType;
import com.manage.product.api.exception.ValueInjectionException;
import com.manage.product.api.util.UtilClass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * Hibernate Listener to inject the value to the field that has the {@link InjectedDate} annotation.
 *
 * @author Luis
 */

public class HibernateEventHandlers {

    @PrePersist
    void prePersist(Object entity) {
        injectRegisterDate(entity, InjectedDateType.REGISTER_DATE);
    }

    @PreUpdate
    void preUpdate(Object entity) {
        injectRegisterDate(entity, InjectedDateType.UPDATE_DATE);
    }

    private void injectRegisterDate(Object entity, InjectedDateType injectedDateType) {
        for (Field field : UtilClass.getFieldsFromEntity(entity)) {
            InjectedDate injectedDate = field.getAnnotation(InjectedDate.class);
            if (injectedDate == null || !injectedDateType.equals(injectedDate.DATE_TYPE())) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null && !(value instanceof LocalDateTime)) {
                    StringBuilder errorMessage = new StringBuilder().append("The data type must be LocalDateTime to the field ")
                            .append("that is annotated with InjectedDate.");
                    throw new ValueInjectionException(errorMessage);
                }
                field.set(entity, LocalDateTime.now());
            } catch (IllegalAccessException e) {
                StringBuilder errorMessage = new StringBuilder().append("Injected Date Error in ")
                        .append(entity.getClass().getName()).append(" :: ").append(e.getMessage());
                throw new ValueInjectionException(errorMessage);
            }

        }
    }

}
