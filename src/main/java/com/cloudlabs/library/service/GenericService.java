package com.cloudlabs.library.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID>{
    T save(T entity);
    Optional<T> findById(ID id);
    T update(T entity);
    void delete(ID id);
    List<T> findAll();
}
