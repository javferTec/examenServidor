package com.fpmislata.basespring.domain.useCase.movie.common.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.domain.service.MovieService;
import com.fpmislata.basespring.domain.useCase.movie.common.MovieCountUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class MovieCountUseCaseImpl implements MovieCountUseCase {

    private final MovieService movieService;

    public int execute() {
        return movieService.count();
    }
}
