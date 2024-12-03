package com.fpmislata.basespring.domain.useCase.movie.admin.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.service.DirectorService;
import com.fpmislata.basespring.domain.service.MovieService;
import com.fpmislata.basespring.domain.common.util.helper.EntityHelper;
import com.fpmislata.basespring.domain.useCase.movie.admin.MovieInsertDirectorUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class MovieInsertDirectorUseCaseImpl implements MovieInsertDirectorUseCase {

    private final MovieService movieService;
    private final DirectorService directorService;
    private final EntityHelper entityHelper;

    @Override
    public void execute(Integer id, Director director) {
        Movie movie = entityHelper.findAndValidateEntity(movieService::getById, id, "Movie");

        Director existingDirector = entityHelper.findAndValidateEntity(directorService::getById, director.getId(), "Director");
        movieService.addDirector(movie, existingDirector);

        movieService.save(movie);
    }
}
