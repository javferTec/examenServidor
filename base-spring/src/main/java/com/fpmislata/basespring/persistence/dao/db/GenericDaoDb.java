package com.fpmislata.basespring.persistence.dao.db;

import java.util.List;
import java.util.Optional;

public interface GenericDaoDb<T> {
    //List<T> getAll();
    int count();
    List<T> getAll(int page, int size);
    Optional<T> getById(Integer id);
    Integer insert(T t);
    void update(T t);
    void delete(Integer id);
}
