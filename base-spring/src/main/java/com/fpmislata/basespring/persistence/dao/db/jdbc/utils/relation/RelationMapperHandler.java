package com.fpmislata.basespring.persistence.dao.db.jdbc.utils.relation;

import com.fpmislata.basespring.common.annotation.persistence.ManyToMany;
import com.fpmislata.basespring.common.annotation.persistence.OneToMany;
import com.fpmislata.basespring.common.annotation.persistence.OneToOne;
import com.fpmislata.basespring.common.exception.MappingException;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.metadata.EntityMetadataExtractor;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.sql.SqlBuilderRelation;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.text.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class RelationMapperHandler<T> {

    private final Class<T> type; // Clase del objeto a mapear
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(RelationMapperHandler.class);
    private final EntityMetadataExtractor<T> metadata;
    private final SqlBuilderRelation<T> sqlBuilderRelation;
    private final DataSource dataSource;


    public RelationMapperHandler(Class<T> type, EntityMetadataExtractor<T> metadata, DataSource dataSource) {
        this.type = type;
        this.metadata = metadata;
        this.dataSource = dataSource;
        this.sqlBuilderRelation = new SqlBuilderRelation<>(metadata);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Metodo para procesar las relaciones (OneToOne, OneToMany, ManyToMany)
    public void processRelationships(ResultSet resultSet, Object instance) throws Exception {
        // Itera sobre los campos de la clase
        for (Field field : type.getDeclaredFields()) {
            // Si tiene la anotacion OneToOne, procesa la relacion
            if (field.isAnnotationPresent(OneToOne.class)) {
                try {
                    mapOneToOne(resultSet, instance, field);
                } catch (Exception e) {
                    logger.warn("Error mapping OneToOne relationship for field '{}': {}", field.getName(), e.getMessage());
                }
            } else if (field.isAnnotationPresent(OneToMany.class)) {
                // Si tiene la anotacion OneToMany, procesa la relacion
                mapOneToMany(instance, field);
            } else if (field.isAnnotationPresent(ManyToMany.class)) {
                // Si tiene la anotacion ManyToMany, procesa la relacion
                mapManyToMany(instance, field);
            }
        }
    }

    // Metodo para mapear una relacion OneToOne
    private void mapOneToOne(ResultSet resultSet, Object instance, Field field) throws Exception {
        OneToOne annotation = field.getAnnotation(OneToOne.class);
        String joinColumn = annotation.joinColumn(); // Obtiene el nombre de la columna FK (clave foranea)
        Class<?> targetEntity = annotation.targetEntity(); // Obtiene la clase de la entidad relacionada

        // Obtiene el valor de la clave foranea desde el ResultSet
        Object foreignKeyValue = resultSet.getObject(joinColumn);
        if (foreignKeyValue != null) {
            String query = String.format("SELECT * FROM %s WHERE id = ?", metadata.getTableName(targetEntity));
            // Realiza una consulta para obtener la entidad relacionada
            Object relatedInstance = jdbcTemplate.queryForObject(query, new GenericRowMapper<>(targetEntity, dataSource), foreignKeyValue);
            setRelatedField(instance, field, relatedInstance); // Establece el valor de la entidad relacionada
        }
    }

    // Metodo para mapear una relacion OneToMany
    private void mapOneToMany(Object instance, Field field) throws Exception {
        OneToMany annotation = field.getAnnotation(OneToMany.class);

        // Verifica que el campo sea una coleccion
        if (!Collection.class.isAssignableFrom(field.getType())) {
            throw new MappingException("Field " + field.getName() + " must be a Collection for @OneToMany");
        }

        // Mapea las entidades relacionadas
        List<?> relatedList = mapRelatedEntities(annotation.targetEntity(), sqlBuilderRelation.buildOneToManyQuery(annotation), instance);

        setRelatedField(instance, field, relatedList); // Establece la lista relacionada en el campo
    }

    // Metodo para mapear una relacion ManyToMany
    private void mapManyToMany(Object instance, Field field) throws Exception {
        ManyToMany annotation = field.getAnnotation(ManyToMany.class);
        List<?> relatedList = mapRelatedEntities(annotation.targetEntity(), sqlBuilderRelation.buildManyToManyQuery(annotation), instance);

        setRelatedField(instance, field, relatedList); // Establece la lista relacionada en el campo
    }

    // Metodo para mapear entidades relacionadas
    private List<?> mapRelatedEntities(Class<?> targetEntity, String query, Object instance) {
        try {
            // Realiza la consulta y mapea las entidades relacionadas
            return jdbcTemplate.query(query,
                    (rs, rowNum) -> new GenericRowMapper<>(targetEntity, dataSource).mapRow(rs, rowNum),
                    metadata.getPrimaryKey(instance));
        } catch (Exception e) {
            logger.error("Error mapping related entities: {}", e.getMessage(), e);
            return Collections.emptyList(); // Devuelve una lista vacia en caso de error
        }
    }

    // Metodo para establecer el valor de una entidad relacionada
    private void setRelatedField(Object instance, Field field, Object value) throws Exception {
        Method setter = type.getMethod("set" + StringUtil.capitalize(field.getName()), field.getType());
        setter.invoke(instance, value); // Llama al setter para establecer el valor
    }

}
