package com.manage.product.service;

import com.manage.product.api.exception.ApplicationBusinessException;
import com.manage.product.model.AbstractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class AbstractService {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    protected <T extends AbstractEntity> T getEntity(Class<? extends AbstractEntity> entity, Long id) {
        String className = entity.getSimpleName();
        String sql = "FROM " + className + " e WHERE e.id = :id";

        return (T) entityManager.createQuery(sql, entity).setParameter("id", id).getResultList().stream()
                .findAny().orElseThrow(() -> {
            String errorMessage = className + " not found. Id " + id;
            return new ApplicationBusinessException(errorMessage);
        });
    }
}
