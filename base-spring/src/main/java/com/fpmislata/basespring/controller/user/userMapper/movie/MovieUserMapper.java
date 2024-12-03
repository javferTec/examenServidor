package com.fpmislata.basespring.controller.user.userMapper.movie;

import com.fpmislata.basespring.common.annotation.common.Mapper;
import com.fpmislata.basespring.controller.common.entity.model.actor.ActorCollection;
import com.fpmislata.basespring.controller.user.userModel.movie.MovieUserDetail;
import com.fpmislata.basespring.domain.model.Movie;
import org.modelmapper.ModelMapper;

import java.util.List;

@Mapper
public class MovieUserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static MovieUserDetail toMovieUserDetail(Movie movie) {
        MovieUserDetail movieUserDetail = modelMapper.map(movie, MovieUserDetail.class);

        if (movie.getDirector() != null) {
            movieUserDetail.setDirector(movie.getDirector().getName());
        }

        if (movie.getActors() != null) {
            List<ActorCollection> actorCollection = movie.getActors().stream()
                    .map(actor -> new ActorCollection(actor.getName()))
                    .toList();
            movieUserDetail.setActors(actorCollection);
        }
        return movieUserDetail;
    }
}
