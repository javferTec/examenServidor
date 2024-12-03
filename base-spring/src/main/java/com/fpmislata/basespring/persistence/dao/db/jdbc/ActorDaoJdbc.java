package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.persistence.dao.db.ActorDaoDb;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Dao
public class ActorDaoJdbc extends BaseDaoJdbc<Actor> implements ActorDaoDb  {

    public ActorDaoJdbc(DataSource dataSource) {
        super(Actor.class, dataSource);
    }

    @Override
    public int count() {
        return super.count();
    }

    @Override
    public List<Actor> getAll(int page, int size) {
        return super.getAll(page, size);
    }

    @Override
    public Optional<Actor> getById(Integer id) {
        return super.getById(id);
    }

    @Override
    public Integer insert(Actor actor) {
        return super.insert(actor);
    }

    @Override
    public void update(Actor actor) {
        super.update(actor);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }
}
