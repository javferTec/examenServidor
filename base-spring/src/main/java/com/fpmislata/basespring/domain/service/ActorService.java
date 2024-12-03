package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Actor;

import java.util.Optional;

public interface ActorService {
    Optional<Actor> getById(Integer id);
}
