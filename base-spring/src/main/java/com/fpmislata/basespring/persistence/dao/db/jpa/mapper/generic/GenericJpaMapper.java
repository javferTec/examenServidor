package com.fpmislata.basespring.persistence.dao.db.jpa.mapper.generic;

import org.mapstruct.Named;

public interface GenericJpaMapper<T, E> {

    // Metodo de mapeo de entidad a dominio
    @Named("toDomain")
    T toDomain(E entity);

    // Metodo de mapeo de dominio a entidad
    @Named("toJpaEntity")
    E toJpaEntity(T domain);

    // Metodo para mapear entidad a dominio con detalles adicionales
    @Named("toDomainWithDetails")
    T toDomainWithDetails(E entity);

    // Metodo para mapear dominio a entidad con detalles adicionales
    @Named("toJpaEntityWithDetails")
    E toJpaEntityWithDetails(T domain);
}