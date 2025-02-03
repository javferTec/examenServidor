package com.fpmislata.basespring.persistence.dao.db.jpa.repository;

import com.fpmislata.basespring.persistence.dao.db.jpa.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {
}
