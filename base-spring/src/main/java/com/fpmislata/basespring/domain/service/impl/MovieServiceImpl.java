package com.fpmislata.basespring.domain.service.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainService;
import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.repository.MovieRepository;
import com.fpmislata.basespring.domain.service.MovieService;
import com.fpmislata.basespring.domain.common.util.helper.EntityHelper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final EntityHelper entityHelper;


    @Override
    public int count() {
        return movieRepository.count();
    }

    @Override
    public List<Movie> getAll(int offset, int size) {
        return movieRepository.getAll(offset, size);
    }

    @Override
    public Optional<Movie> getById(Integer id) {
        return movieRepository.getById(id);
    }

    @Override
    public void delete(Integer id) {
        movieRepository.delete(id);
    }

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void addActor(Movie movie, Actor actor) {
        if (movie.getActors() == null) {
            movie.setActors(new ArrayList<>());
        }

        entityHelper.validateEntityNotInCollection(
                movie.getActors(),
                existingActor -> existingActor.getId().equals(actor.getId()),
                "Actor " + actor.getName()
        );

        movie.addActors(actor);
    }

    @Override
    public void addDirector(Movie movie, Director director) {
        entityHelper.validateEntityNotEqual(
                movie.getDirector(),
                existingDirector -> existingDirector.getId().equals(director.getId()),
                "Director " + director.getName()
        );

        movie.addDirector(director);
    }
}
