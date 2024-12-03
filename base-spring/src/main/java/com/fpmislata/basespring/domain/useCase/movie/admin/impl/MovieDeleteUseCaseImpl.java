package com.fpmislata.basespring.domain.useCase.movie.admin.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.domain.service.MovieService;
import com.fpmislata.basespring.domain.common.util.helper.EntityHelper;
import com.fpmislata.basespring.domain.useCase.movie.admin.MovieDeleteUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class MovieDeleteUseCaseImpl implements MovieDeleteUseCase {

    private final MovieService movieService;
    private final EntityHelper entityHelper;

    @Override
    public void execute(Integer id) {
        entityHelper.validateEntityExist(movieService::getById, id, "Movie");
        movieService.delete(id);
    }
}
