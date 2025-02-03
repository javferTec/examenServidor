package com.fpmislata.basespring.persistence.dao.db.jpa;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.persistence.dao.db.ActorDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jpa.entity.ActorEntity;
import com.fpmislata.basespring.persistence.dao.db.jpa.mapper.specific.ActorJpaMapper;
import com.fpmislata.basespring.persistence.dao.db.jpa.repository.ActorJpaRepository;

@Dao
public class ActorDaoJpa
        extends GenericDaoJpa<Actor, ActorEntity, ActorJpaRepository, ActorJpaMapper>
        implements ActorDaoDb {


    protected ActorDaoJpa(ActorJpaRepository repository, ActorJpaMapper mapper) {
        super(repository, mapper);
    }

}
