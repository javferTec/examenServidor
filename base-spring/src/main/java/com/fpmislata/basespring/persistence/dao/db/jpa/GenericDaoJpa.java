package com.fpmislata.basespring.persistence.dao.db.jpa;

import com.fpmislata.basespring.domain.model.ListWithCount;
import com.fpmislata.basespring.persistence.dao.db.GenericDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jpa.mapper.generic.GenericJpaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Clase abstracta que implementa la interfaz GenericDaoDb y define los métodos
 * comunes para los DAOs JPA.
 *
 * @param <T> Tipo de la entidad de dominio.
 * @param <E> Tipo de la entidad JPA.
 * @param <R> Tipo del repositorio JPA.
 * @param <M> Tipo del mapeador JPA.
 */

public abstract class GenericDaoJpa<T, E, R extends JpaRepository<E, Long>, M extends GenericJpaMapper<T, E>>
        implements GenericDaoDb<T> {

    protected final R repository;
    protected final M mapper;

    protected GenericDaoJpa(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }



    @Override
    public ListWithCount<T> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<E> pageResult = repository.findAll(pageable);
        return new ListWithCount<>(
                pageResult.getContent()
                        .stream()
                        .map(mapper::toDomain)
                        .toList(),
                pageResult.getTotalElements()
        );
    }

    @Override
    public Optional<T> getById(Integer id) {
        return repository.findById(Long.valueOf(id))
                .map(mapper::toDomainWithDetails);
    }

    @Override
    public Integer insert(T domain) {
        E entity = mapper.toJpaEntity(domain);
        E savedEntity = repository.save(entity);
        return Math.toIntExact(extractId(savedEntity));
    }

    @Override
    public void update(T domain) {
        repository.save(mapper.toJpaEntity(domain));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(Long.valueOf(id));
    }

    @Override
    public int count() {
        return (int) repository.count();
    }

    @Override
    public T save(T domain) {
        E entity = repository.save(mapper.toJpaEntity(domain));
        return mapper.toDomain(entity);
    }

    /**
     * Extrae el ID de la entidad JPA utilizando reflexión.
     */
    private long extractId(E entity) {
        try {
            Method getIdMethod = entity.getClass().getMethod("getId");
            return (Integer) getIdMethod.invoke(entity);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo extraer el ID de la entidad: " + entity.getClass().getName(), e);
        }
    }
}
