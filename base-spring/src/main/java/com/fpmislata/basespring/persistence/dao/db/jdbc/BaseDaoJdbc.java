package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.persistence.dao.db.GenericDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.metadata.EntityMetadataExtractor;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.operation.OperationType;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.relation.RelationOperationHandler;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.sql.SqlBuilderOperation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.*;

public class BaseDaoJdbc<T> implements GenericDaoDb<T> {

    // Variables de instancia
    protected final Class<T> entityClass; // Representa la clase de la entidad generica
    private final JdbcTemplate jdbcTemplate; // Permite ejecutar consultas SQL
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate; // Permite el uso de parametros nombrados en consultas
    private final GenericRowMapper<T> rowMapper; // Mapea las filas del resultado SQL a objetos de la clase generica
    private final EntityMetadataExtractor<T> entityMetadataExtractor; // Extrae los valores de las columnas de una entidad en un mapa clave-valor
    private final RelationOperationHandler<T> relationOperationHandler; // Maneja las relaciones entre entidades
    private final SqlBuilderOperation sqlBuilderOperation; // Construye SQL de inserción, actualización y criterios WHERE

    // Constructor
    // Inicializa las variables de instancia con los parametros proporcionados
    public BaseDaoJdbc(Class<T> entityClass, DataSource dataSource) {
        this.entityClass = entityClass;
        this.relationOperationHandler = new RelationOperationHandler<>(entityClass, dataSource);
        this.entityMetadataExtractor = new EntityMetadataExtractor<>(entityClass);
        this.sqlBuilderOperation = new SqlBuilderOperation();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource); // dataSource es un objeto que representa la fuente de datos
        this.rowMapper = new GenericRowMapper<>(entityClass, dataSource);
    }


    // Ejecuta una consulta SQL personalizada que devuelve una unica entidad
    public T customSqlQuery(String sql, Map<String, ?> params) {
        try {
            return (params != null && !params.isEmpty())
                    ? namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper)
                    : jdbcTemplate.queryForObject(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Ejecuta una consulta SQL personalizada que devuelve una lista de entidades
    public List<T> customSqlQueryForList(String sql, Map<String, ?> params) {
        return (params != null && !params.isEmpty())
                ? namedParameterJdbcTemplate.query(sql, params, rowMapper)
                : jdbcTemplate.query(sql, rowMapper);
    }

    // Recupera todas las entidades de la tabla asociada
    public List<T> getAll() {
        return jdbcTemplate.query(entityMetadataExtractor.getSelectTable(), rowMapper);
    }

    // Recupera una pagina especifica de entidades
    public List<T> getAll(int page, int size) {
        // Aseguramos que el OFFSET no sea negativo
        //int offset = Math.max(0, (page - 1) * size);
        String sql = entityMetadataExtractor.getSelectTable() + " LIMIT " + size + " OFFSET " + page;
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Recupera entidades por un array de IDs
    public List<T> getAllByIds(Integer[] ids) {
        String sql = entityMetadataExtractor.getSelectTable() + " WHERE id IN (:ids)";
        Map<String, List<Integer>> params = Map.of("ids", Arrays.asList(ids));
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    // Recupera entidades segun un mapa de criterios
    public List<T> getByCriteria(Map<String, Object> criteria) {
        String sql = sqlBuilderOperation.buildSqlWithCriteria(entityMetadataExtractor.getSelectTable(), criteria);
        return namedParameterJdbcTemplate.query(sql, criteria, rowMapper);
    }

    // Recupera una entidad por su ID como un Optional
    public Optional<T> getById(Integer id) {
        return queryForOptional(entityMetadataExtractor.getSelectTable() + " WHERE id = ?", id);
    }

    // Recupera una entidad usando su clave primaria
    public Optional<T> getByPrimaryKey(Integer id) {
        return queryForOptional("SELECT * FROM " + entityMetadataExtractor.getTableName() + " WHERE " + entityMetadataExtractor.getPrimaryKeyColumn() + " = ?", id);
    }

    // Cuenta el numero total de entidades en la tabla
    public int count() {
        String sql = "SELECT COUNT(*) FROM " + entityMetadataExtractor.getTableName();
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;
    }

    // Realiza una consulta que devuelve un Optional
    private Optional<T> queryForOptional(String sql, Object... params) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, params));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // Inserta una nueva entidad en la base de datos
    public Integer insert(T entity) {
        Map<String, Object> values = entityMetadataExtractor.extractColumnValues(entity);
        String sql = sqlBuilderOperation.buildInsertSql(entityMetadataExtractor.getTableName(), values);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(values), keyHolder);
        int generatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        relationOperationHandler.handleRelations(entity, generatedId, OperationType.INSERT);
        return generatedId;
    }

    // Actualiza una entidad existente en la base de datos
    public void update(T entity) {
        Map<String, Object> values = entityMetadataExtractor.extractColumnValues(entity);
        String sql = sqlBuilderOperation.buildUpdateSql(entityMetadataExtractor.getTableName(), values, entityMetadataExtractor.getPrimaryKeyColumn());
        namedParameterJdbcTemplate.update(sql, values);
        relationOperationHandler.handleRelations(entity, (Integer) values.get(entityMetadataExtractor.getPrimaryKeyColumn()), OperationType.UPDATE);
    }

    // Elimina una entidad por su ID
    public void delete(Integer id) {
        relationOperationHandler.handleRelationsBeforeDelete(id);
        String sql = "DELETE FROM " + entityMetadataExtractor.getTableName() + " WHERE " + entityMetadataExtractor.getPrimaryKeyColumn() + " = ?";
        jdbcTemplate.update(sql, id);
    }

    public String selectTable() {
        return entityMetadataExtractor.getSelectTable();
    }

}