package com.fpmislata.basespring.persistence.dao.db;

import com.fpmislata.basespring.domain.model.ListWithCount;

import java.util.Optional;

public interface GenericDaoDb<T> {
    //List<T> getAll();
    int count();
    ListWithCount<T> getAll(int page, int size);
    Optional<T> getById(Integer id);
    Integer insert(T t);
    void update(T t);
    void delete(Integer id);
    T save(T t);
}
