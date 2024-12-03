package com.fpmislata.basespring.domain.useCase.movie.common;

import com.fpmislata.basespring.domain.model.Movie;

import java.util.List;

public interface MovieGetAllUseCase {
    List<Movie> execute(int offset, int size);
}
