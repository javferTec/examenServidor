package com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic;

import com.fpmislata.basespring.common.exception.MappingException;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.cache.ReflectionColumnFieldCache;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.metadata.EntityMetadataExtractor;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.relation.RelationMapperHandler;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.text.StringUtil;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Map;

public class GenericRowMapper<T> implements RowMapper<T> {

    // Se crea un logger para la clase se utiliza para registrar mensajes de log en diferentes niveles de severidad (info, debug, error, etc.)
    private static final Logger logger = LoggerFactory.getLogger(GenericRowMapper.class);

    private final Class<T> type; // Clase del objeto a mapear
    private final EntityMetadataExtractor<T> metadata;
    private final RelationMapperHandler<T> relationMapperHandler;

    public GenericRowMapper(Class<T> type, DataSource dataSource) {
        this.type = type;
        this.metadata = new EntityMetadataExtractor<>(type);
        this.relationMapperHandler = new RelationMapperHandler<>(type, metadata, dataSource);
    }

    // Metodo que mapea una fila de la base de datos a un objeto de tipo T (Generico)
    @Override
    public T mapRow(@NonNull ResultSet resultSet, int rowNum) {
        try {
            // Se crea una nueva instancia del objeto T
            T instance = type.getDeclaredConstructor().newInstance();
            // Se obtienen los campos de la clase utilizando un cache
            Map<String, Field> fields = ReflectionColumnFieldCache.getCachedFields(type);

            // Mapea los campos simples
            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                // Se obtiene el nombre de la columna
                String columnName = metadata.getColumnName(entry.getValue());
                // Se asigna el valor al campo del objeto
                setFieldValue(instance, resultSet, entry.getValue(), columnName);
            }

            // Procesa las relaciones (OneToOne, OneToMany, ManyToMany)
            relationMapperHandler.processRelationships(resultSet, instance);

            return instance;
        } catch (Exception e) {
            // Se captura cualquier excepcion y se loguea
            logger.error("Error mapping row to {}: {}", type.getSimpleName(), e.getMessage(), e);
            throw new MappingException("Error mapping row to " + type.getSimpleName(), e);
        }
    }


    // Metodo para establecer el valor de un campo en el objeto
    private void setFieldValue(T instance, ResultSet resultSet, Field field, String columnName) throws Exception {
        String setterName = "set" + StringUtil.capitalize(field.getName()); // Crea el nombre del setter
        Method setter = type.getMethod(setterName, field.getType()); // Obtiene el metodo setter

        // Verifica si la columna existe en el resultado
        if (metadata.columnExists(resultSet, columnName)) {
            // Si la columna existe, obtiene el valor y lo establece en el campo
            Object value = resultSet.getObject(columnName, field.getType());
            logger.debug("Setting field '{}' with value '{}' from column '{}'", field.getName(), value, columnName);
            setter.invoke(instance, value); // Llama al setter
        } else {
            // Si la columna no existe, se muestra una advertencia
            logger.warn("Column '{}' does not exist in result set for field '{}' of entity {}", columnName, field.getName(), type.getSimpleName());
        }
    }


}
