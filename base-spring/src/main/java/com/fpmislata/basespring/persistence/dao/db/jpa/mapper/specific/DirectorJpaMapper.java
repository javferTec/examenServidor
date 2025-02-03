package com.fpmislata.basespring.persistence.dao.db.jpa.mapper.specific;

import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.persistence.dao.db.jpa.entity.DirectorEntity;
import com.fpmislata.basespring.persistence.dao.db.jpa.mapper.generic.GenericJpaMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorJpaMapper extends GenericJpaMapper<Director, DirectorEntity> {
}
