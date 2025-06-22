package com.yourcompany.yourapp.infrastructure.persistence.base;

import java.util.List;
import java.util.Optional;

/*
Usage Example (in a service)

 * @Autowired
private GenericRepository<MyEntity, Long> myEntityRepository;

*/

public interface GenericRepository<T extends BaseEntity, ID> {

    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T entity);

    void delete(T entity);

    T update(T entity);

    List<T> findByField(String fieldName, Object value);
}
