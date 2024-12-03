package com.fpmislata.basespring.domain.useCase.movie.admin.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.service.ActorService;
import com.fpmislata.basespring.domain.service.MovieService;
import com.fpmislata.basespring.domain.common.util.helper.EntityHelper;
import com.fpmislata.basespring.domain.useCase.movie.admin.MovieInsertActorsUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class MovieInsertActorsUseCaseImpl implements MovieInsertActorsUseCase {

    private final MovieService movieService;
    private final ActorService actorService;
    private final EntityHelper entityHelper;

    @Override
    public void execute(Integer id, List<Actor> actors) {
        Movie movie = entityHelper.findAndValidateEntity(movieService::getById, id, "Movie");

        actors.forEach(actor -> {
            Actor existingActor = entityHelper.findAndValidateEntity(actorService::getById, actor.getId(), "Actor");
            movieService.addActor(movie, existingActor);
        });

        movieService.save(movie);
    }
}
