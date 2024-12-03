package com.fpmislata.basespring.domain.useCase.movie.common;

import com.fpmislata.basespring.domain.model.Movie;

public interface MovieGetByIdUseCase {
    Movie execute(Integer id);
}
