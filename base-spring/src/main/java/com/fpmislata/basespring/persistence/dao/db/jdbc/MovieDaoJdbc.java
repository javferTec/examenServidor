package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.persistence.dao.db.MovieDaoDb;

import javax.sql.DataSource;

@Dao
public class MovieDaoJdbc extends BaseDaoJdbc<Movie> implements MovieDaoDb {

    public MovieDaoJdbc(DataSource dataSource) {
        super(Movie.class, dataSource);
    }

    @Override
    public int count() {
        return super.count();
    }

    @Override
    public Integer insert(Movie movie) {
        return super.insert(movie);
    }

    @Override
    public void update(Movie movie) {
        super.update(movie);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

}
