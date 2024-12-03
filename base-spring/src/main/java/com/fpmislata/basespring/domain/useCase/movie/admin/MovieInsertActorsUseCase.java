package com.fpmislata.basespring.domain.useCase.movie.admin;

import com.fpmislata.basespring.domain.model.Actor;

import java.util.List;

public interface MovieInsertActorsUseCase {
    void execute(Integer id, List<Actor> actors);
}
