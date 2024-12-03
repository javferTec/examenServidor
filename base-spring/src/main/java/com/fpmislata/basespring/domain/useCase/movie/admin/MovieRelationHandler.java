package com.fpmislata.basespring.domain.useCase.movie.admin;

import com.fpmislata.basespring.domain.model.Movie;

public interface MovieRelationHandler {
    void resolveRelations(Movie movie);
}
