package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    int count();
    List<Movie> getAll(int offset, int size);
    Optional<Movie> getById(Integer id);
    void delete(Integer id);
    void save(Movie movie);
}
