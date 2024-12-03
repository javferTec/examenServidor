package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.domain.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    int count();
    List<Movie> getAll(int offset, int size);
    Optional<Movie> getById(Integer id);
    void delete(Integer id);
    void save(Movie movie);
    void addActor(Movie movie, Actor actor);
    void addDirector(Movie movie, Director director);
}
