package com.fpmislata.basespring.persistence.repository.impl;

import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.domain.repository.ActorRepository;
import com.fpmislata.basespring.persistence.dao.db.ActorDaoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ActorRepositoryJdbc implements ActorRepository {

    private final ActorDaoDb actorDaoDb;

    @Override
    public Optional<Actor> getById(Integer id) {
        return actorDaoDb.getById(id);
    }
}
