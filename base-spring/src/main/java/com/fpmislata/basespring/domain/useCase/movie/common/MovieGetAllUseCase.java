package com.fpmislata.basespring.domain.useCase.movie.common;

import com.fpmislata.basespring.domain.model.ListWithCount;
import com.fpmislata.basespring.domain.model.Movie;

public interface MovieGetAllUseCase {
    ListWithCount<Movie> execute(int offset, int size);
}
