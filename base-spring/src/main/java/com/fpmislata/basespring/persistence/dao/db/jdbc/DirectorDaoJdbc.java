package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.persistence.dao.db.DirectorDaoDb;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Dao
public class DirectorDaoJdbc extends BaseDaoJdbc<Director> implements DirectorDaoDb {

    public DirectorDaoJdbc(DataSource dataSource) {
        super(Director.class, dataSource);
    }

    @Override
    public int count() {
        return super.count();
    }

    @Override
    public List<Director> getAll(int page, int size) {
        return super.getAll(page, size);
    }

    @Override
    public Optional<Director> getById(Integer id) {
        return super.getById(id);
    }

    @Override
    public Integer insert(Director director) {
        return super.insert(director);
    }

    @Override
    public void update(Director director) {
        super.update(director);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }
}
