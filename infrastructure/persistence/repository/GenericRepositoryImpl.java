package com.yourcompany.yourapp.infrastructure.persistence.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenericRepositoryImpl<T extends BaseEntity, ID> implements GenericRepository<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityType;

    public void setEntityType(Class<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    @Override
    public List<T> findAll() {
        String ql = "SELECT e FROM " + entityType.getSimpleName() + " e";
        return entityManager.createQuery(ql, entityType).getResultList();
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        entity.setDeleted(true);
        entityManager.merge(entity);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<T> findByField(String fieldName, Object value) {
        String jpql = "SELECT e FROM " + entityType.getSimpleName() + " e WHERE e." + fieldName + " = :value";
        TypedQuery<T> query = entityManager.createQuery(jpql, entityType);
        query.setParameter("value", value);
        return query.getResultList();
    }
}
