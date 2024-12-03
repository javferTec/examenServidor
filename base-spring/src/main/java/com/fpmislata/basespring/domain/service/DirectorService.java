package com.fpmislata.basespring.domain.service;

import com.fpmislata.basespring.domain.model.Director;

import java.util.Optional;

public interface DirectorService {
    Optional<Director> getById(Integer id);
}
