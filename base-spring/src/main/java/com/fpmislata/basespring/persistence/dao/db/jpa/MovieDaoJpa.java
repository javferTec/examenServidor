package com.fpmislata.basespring.persistence.dao.db.jpa;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.persistence.dao.db.MovieDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jpa.entity.MovieEntity;
import com.fpmislata.basespring.persistence.dao.db.jpa.mapper.specific.MovieJpaMapper;
import com.fpmislata.basespring.persistence.dao.db.jpa.repository.MovieJpaRepository;

@Dao
public class MovieDaoJpa
        extends GenericDaoJpa<Movie, MovieEntity, MovieJpaRepository, MovieJpaMapper>
        implements MovieDaoDb {

    protected MovieDaoJpa(MovieJpaRepository repository, MovieJpaMapper mapper) {
        super(repository, mapper);
    }
}
