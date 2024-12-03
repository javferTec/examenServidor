package com.fpmislata.basespring.domain.service.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainService;
import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.domain.repository.ActorRepository;
import com.fpmislata.basespring.domain.service.ActorService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    @Override
    public Optional<Actor> getById(Integer id) {
        return actorRepository.getById(id);
    }
}
