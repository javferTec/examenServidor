package com.fpmislata.basespring.persistence.dao.db.jpa.repository;

import com.fpmislata.basespring.persistence.dao.db.jpa.entity.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorJpaRepository extends JpaRepository<DirectorEntity, Long> {
}
