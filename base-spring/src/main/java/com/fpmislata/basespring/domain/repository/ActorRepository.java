package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Actor;

import java.util.Optional;

public interface ActorRepository {
    Optional<Actor> getById(Integer id);
}
