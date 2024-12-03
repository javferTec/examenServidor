package com.fpmislata.basespring.domain.repository;

import com.fpmislata.basespring.domain.model.Director;

import java.util.Optional;

public interface DirectorRepository {
    Optional<Director> getById(Integer id);
}
