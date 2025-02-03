package com.fpmislata.basespring.persistence.dao.db.jpa.mapper.specific;

import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.persistence.dao.db.jpa.entity.ActorEntity;
import com.fpmislata.basespring.persistence.dao.db.jpa.mapper.generic.GenericJpaMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorJpaMapper extends GenericJpaMapper<Actor, ActorEntity> {
}
