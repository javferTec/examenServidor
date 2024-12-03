package com.fpmislata.basespring.persistence.repository.impl;

import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.repository.MovieRepository;
import com.fpmislata.basespring.persistence.dao.db.MovieDaoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryJdbc implements MovieRepository {

    private final MovieDaoDb movieDaoDb;

    @Override
    public int count() {
        return movieDaoDb.count();
    }

    @Override
    public List<Movie> getAll(int offset, int size) {
        return movieDaoDb.getAll(offset, size);
    }

    @Override
    public Optional<Movie> getById(Integer id) {
        return movieDaoDb.getById(id);
    }

    @Override
    public void delete(Integer id) {
        movieDaoDb.delete(id);
    }

    @Override
    public void save(Movie movie) {
        if (movie.getId() != null) {
            movieDaoDb.update(movie);
        } else {
            Integer id = movieDaoDb.insert(movie);
            movie.setId(id);
        }
    }
}
