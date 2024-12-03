package com.fpmislata.basespring.persistence.dao.db.jdbc.utils.relation;

import com.fpmislata.basespring.common.annotation.persistence.ManyToMany;
import com.fpmislata.basespring.common.annotation.persistence.OneToMany;
import com.fpmislata.basespring.common.annotation.persistence.OneToOne;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.metadata.EntityMetadataExtractor;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.operation.OperationType;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.sql.SqlBuilderOperation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class RelationOperationHandler<T> {

    private final Class<T> entityClass;
    private final EntityMetadataExtractor<T> metadata;
    private final JdbcTemplate jdbcTemplate;
    private final SqlBuilderOperation sqlBuilderOperation;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RelationOperationHandler(Class<T> entityClass, DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.entityClass = entityClass;
        this.metadata = new EntityMetadataExtractor<>(entityClass);
        this.sqlBuilderOperation = new SqlBuilderOperation();
    }


    // Maneja las relaciones entre entidades cuando se realiza una operación (INSERT, UPDATE, DELETE)
    public void handleRelations(T entity, Integer parentId, OperationType operationType) {
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true); // Permite el acceso a campos privados
            try {
                if (field.isAnnotationPresent(OneToOne.class)) {
                    handleOneToOneRelation(entity, field, parentId, operationType);
                } else if (field.isAnnotationPresent(OneToMany.class)) {
                    handleOneToManyRelation(entity, field, parentId, operationType);
                } else if (field.isAnnotationPresent(ManyToMany.class)) {
                    handleManyToManyRelation(entity, field, parentId, operationType);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to handle relation for field: " + field.getName(), e);
            }
        }
    }

    // Maneja relaciones OneToOne al insertar o actualizar una entidad
    public void handleOneToOneRelation(T entity, Field field, Integer parentId, OperationType operationType) throws IllegalAccessException {
        OneToOne annotation = field.getAnnotation(OneToOne.class);
        Object relatedEntity = field.get(entity); // Obtiene la entidad relacionada

        if (relatedEntity != null) {
            Map<String, Object> relatedValues = metadata.extractColumnValues(relatedEntity);
            String relatedTableName = metadata.getTableName(relatedEntity.getClass());
            String joinColumn = annotation.joinColumn(); // Columna de la relación en la tabla principal

            if (relatedValues.containsKey(metadata.getPrimaryKeyColumn(relatedEntity.getClass())) &&
                    relatedValues.get(metadata.getPrimaryKeyColumn(relatedEntity.getClass())) != null) {
                // La entidad relacionada ya existe, actualizamos la tabla principal con su ID
                Integer relatedId = (Integer) relatedValues.get(metadata.getPrimaryKeyColumn(relatedEntity.getClass()));
                String updateSql = "UPDATE " + metadata.getTableName() + " SET " + joinColumn + " = ? WHERE " + metadata.getPrimaryKeyColumn() + " = ?";
                jdbcTemplate.update(updateSql, relatedId, parentId);

            } else if (operationType == OperationType.INSERT) {
                // Si es una nueva entidad relacionada, primero la insertamos
                String sql = sqlBuilderOperation.buildInsertSql(relatedTableName, relatedValues);
                KeyHolder keyHolder = new GeneratedKeyHolder();
                namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(relatedValues), keyHolder);
                Integer relatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();

                // Luego actualizamos la tabla principal con el ID de la entidad relacionada
                String updateSql = "UPDATE " + metadata.getTableName() + " SET " + joinColumn + " = ? WHERE " + metadata.getPrimaryKeyColumn() + " = ?";
                jdbcTemplate.update(updateSql, relatedId, parentId);
            }
        }
    }

    // Maneja relaciones OneToMany al insertar o actualizar una entidad
    public void handleOneToManyRelation(T entity, Field field, Integer parentId, OperationType operationType) throws IllegalAccessException {
        OneToMany annotation = field.getAnnotation(OneToMany.class);
        Collection<?> relatedEntities = (Collection<?>) field.get(entity); // Obtiene las entidades relacionadas
        if (relatedEntities != null) {
            for (Object relatedEntity : relatedEntities) {
                Map<String, Object> relatedValues = metadata.extractColumnValues(relatedEntity);
                String relatedTableName = metadata.getTableName(relatedEntity.getClass());
                String mappedBy = annotation.mappedBy(); // Clave foránea que conecta las tablas

                relatedValues.put(mappedBy, parentId); // Configura la clave foránea en la entidad relacionada

                if (operationType == OperationType.INSERT) {
                    String sql = sqlBuilderOperation.buildInsertSql(relatedTableName, relatedValues);
                    namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(relatedValues));
                } else if (operationType == OperationType.UPDATE) {
                    String primaryKey = metadata.getPrimaryKeyColumn(relatedEntity.getClass());
                    String sql = sqlBuilderOperation.buildUpdateSql(relatedTableName, relatedValues, primaryKey);
                    namedParameterJdbcTemplate.update(sql, relatedValues);
                }
            }
        }
    }

    // Maneja relaciones ManyToMany al insertar o actualizar una entidad
    public void handleManyToManyRelation(T entity, Field field, Integer parentId, OperationType operationType) throws IllegalAccessException {
        ManyToMany annotation = field.getAnnotation(ManyToMany.class);
        Collection<?> relatedEntities = (Collection<?>) field.get(entity); // Obtiene las entidades relacionadas
        String joinTable = annotation.joinTable(); // Tabla de unión
        String joinColumn = annotation.joinColumn(); // Columna de unión de la tabla principal
        String inverseJoinColumn = annotation.inverseJoinColumn(); // Columna de unión de la entidad relacionada

        if (operationType == OperationType.UPDATE || operationType == OperationType.DELETE) {
            // Elimina relaciones existentes antes de una actualización o eliminación
            String deleteSql = "DELETE FROM " + joinTable + " WHERE " + joinColumn + " = ?";
            jdbcTemplate.update(deleteSql, parentId);
        }

        if (operationType != OperationType.DELETE && relatedEntities != null) {
            for (Object relatedEntity : relatedEntities) {
                Map<String, Object> relatedValues = metadata.extractColumnValues(relatedEntity);
                Integer relatedId = (Integer) relatedValues.get(metadata.getPrimaryKeyColumn(relatedEntity.getClass())); // Obtiene el ID de la entidad relacionada

                String checkSql = "SELECT COUNT(*) FROM " + joinTable + " WHERE " + joinColumn + " = ? AND " + inverseJoinColumn + " = ?";
                int count = jdbcTemplate.queryForObject(checkSql, Integer.class, parentId, relatedId);

                if (count == 0) {
                    // Inserta en la tabla de unión si la relación no existe
                    String insertJoinSql = "INSERT INTO " + joinTable + " (" + joinColumn + ", " + inverseJoinColumn + ") VALUES (?, ?)";
                    jdbcTemplate.update(insertJoinSql, parentId, relatedId);
                }
            }
        }
    }

    // Maneja relaciones ManyToMany antes de eliminar una entidad
    public void handleRelationsBeforeDelete(Integer parentId) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToMany.class)) {
                ManyToMany annotation = field.getAnnotation(ManyToMany.class);
                String sql = "DELETE FROM " + annotation.joinTable() + " WHERE " + annotation.joinColumn() + " = ?";
                jdbcTemplate.update(sql, parentId);
            }
        }
    }

}
