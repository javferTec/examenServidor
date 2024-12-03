package com.fpmislata.basespring.persistence.repository.impl;

import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.domain.repository.DirectorRepository;
import com.fpmislata.basespring.persistence.dao.db.DirectorDaoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DirectorRepositoryJdbc implements DirectorRepository {

    private final DirectorDaoDb directorDaoDb;

    @Override
    public Optional<Director> getById(Integer id) {
        return directorDaoDb.getById(id);
    }
}
