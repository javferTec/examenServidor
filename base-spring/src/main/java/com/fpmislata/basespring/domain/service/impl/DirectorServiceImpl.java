package com.fpmislata.basespring.domain.service.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainService;
import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.domain.repository.DirectorRepository;
import com.fpmislata.basespring.domain.service.DirectorService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    @Override
    public Optional<Director> getById(Integer id) {
        return directorRepository.getById(id);
    }
}
