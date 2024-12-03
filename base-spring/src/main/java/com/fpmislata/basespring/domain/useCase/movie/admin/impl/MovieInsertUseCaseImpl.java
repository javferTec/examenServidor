package com.fpmislata.basespring.domain.useCase.movie.admin.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.service.MovieService;
import com.fpmislata.basespring.domain.common.util.helper.EntityHelper;
import com.fpmislata.basespring.domain.useCase.movie.admin.MovieInsertUseCase;
import com.fpmislata.basespring.domain.useCase.movie.admin.MovieRelationHandler;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class MovieInsertUseCaseImpl implements MovieInsertUseCase {

    private final MovieService movieService;
    private final MovieRelationHandler movieRelationHandler;
    private final EntityHelper entityHelper;

    @Override
    public void execute(Movie movie) {
        entityHelper.validateEntityDoesNotExist(movieService::getById, movie.getId(), "Movie");

        movieRelationHandler.resolveRelations(movie);
        movieService.save(movie);
    }
}
