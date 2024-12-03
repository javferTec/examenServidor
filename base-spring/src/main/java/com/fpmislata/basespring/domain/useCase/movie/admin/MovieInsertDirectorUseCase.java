package com.fpmislata.basespring.domain.useCase.movie.admin;

import com.fpmislata.basespring.domain.model.Director;

public interface MovieInsertDirectorUseCase {

    void execute(Integer id, Director director);
}
