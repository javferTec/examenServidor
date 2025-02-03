package com.fpmislata.basespring.persistence.dao.db.jpa.mapper.specific;

import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.persistence.dao.db.jpa.entity.MovieEntity;
import com.fpmislata.basespring.persistence.dao.db.jpa.mapper.generic.GenericJpaMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DirectorJpaMapper.class, ActorJpaMapper.class})
public interface MovieJpaMapper extends GenericJpaMapper<Movie, MovieEntity> {
}
