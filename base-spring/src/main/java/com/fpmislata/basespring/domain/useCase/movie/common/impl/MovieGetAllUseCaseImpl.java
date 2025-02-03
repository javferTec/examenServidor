package com.fpmislata.basespring.domain.useCase.movie.common.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.domain.model.ListWithCount;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.service.MovieService;
import com.fpmislata.basespring.domain.useCase.movie.common.MovieGetAllUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class MovieGetAllUseCaseImpl implements MovieGetAllUseCase {

    private final MovieService movieService;

    public ListWithCount<Movie> execute(int offset, int size) {
        return movieService.getAll(offset, size);
    }
}
