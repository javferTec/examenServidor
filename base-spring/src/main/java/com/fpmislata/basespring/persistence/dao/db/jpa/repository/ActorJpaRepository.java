package com.fpmislata.basespring.persistence.dao.db.jpa.repository;

import com.fpmislata.basespring.persistence.dao.db.jpa.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorJpaRepository extends JpaRepository<ActorEntity, Long> {
}
