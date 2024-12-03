package com.fpmislata.basespring.domain.useCase.movie.admin.impl;

import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.service.ActorService;
import com.fpmislata.basespring.domain.service.DirectorService;
import com.fpmislata.basespring.domain.common.util.helper.EntityHelper;
import com.fpmislata.basespring.domain.useCase.movie.admin.MovieRelationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieRelationHandlerImpl implements MovieRelationHandler {

    private final DirectorService directorService;
    private final ActorService actorService;
    private final EntityHelper entityHelper;

    @Override
    public void resolveRelations(Movie movie) {

        // Validar y establecer el director
        Director director = entityHelper.findAndValidateEntity(directorService::getById, movie.getDirector().getId(), "Director");
        movie.setDirector(director);

        // Validar y establecer los actores
        List<Actor> actors = movie.getActors().stream()
                .map(actor -> entityHelper.findAndValidateEntity(actorService::getById, actor.getId(), "Actor"))
                .toList();
        movie.setActors(actors);
    }
}
