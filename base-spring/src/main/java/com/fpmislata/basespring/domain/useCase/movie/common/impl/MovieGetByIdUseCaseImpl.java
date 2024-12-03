package com.fpmislata.basespring.domain.useCase.movie.common.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.service.MovieService;
import com.fpmislata.basespring.domain.common.util.helper.EntityHelper;
import com.fpmislata.basespring.domain.useCase.movie.common.MovieGetByIdUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class MovieGetByIdUseCaseImpl implements MovieGetByIdUseCase {

    private final MovieService movieService;
    private final EntityHelper entityHelper;

    @Override
    public Movie execute(Integer id) {
        return entityHelper.findAndValidateEntity(movieService::getById, id, "Movie");
    }
}
