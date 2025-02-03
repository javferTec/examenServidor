package com.fpmislata.basespring.persistence.dao.db.jpa;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.persistence.dao.db.DirectorDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jpa.entity.DirectorEntity;
import com.fpmislata.basespring.persistence.dao.db.jpa.mapper.specific.DirectorJpaMapper;
import com.fpmislata.basespring.persistence.dao.db.jpa.repository.DirectorJpaRepository;

@Dao
public class DirectorDaoJpa
        extends GenericDaoJpa<Director, DirectorEntity, DirectorJpaRepository, DirectorJpaMapper>
        implements DirectorDaoDb {


    protected DirectorDaoJpa(DirectorJpaRepository repository, DirectorJpaMapper mapper) {
        super(repository, mapper);
    }

}
