package com.fpmislata.basespring.persistence.dao.db.jdbc.utils.cache;

import com.fpmislata.basespring.common.annotation.persistence.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionColumnFieldCache {
    // Declaracion de una constante CACHE que almacena un mapa con las clases y sus campos. Es un ConcurrentHashMap para permitir acceso seguro en entornos multihilo
    private static final Map<Class<?>, Map<String, Field>> CACHE = new ConcurrentHashMap<>();

    // Metodo publico que recibe una clase y devuelve un mapa con los campos de esa clase que estan anotados con @Column
    public static Map<String, Field> getCachedFields(Class<?> clazz) {
        // Si la clase ya tiene los campos en el cache, los devuelve; si no, los extrae usando el metodo extractFields
        return CACHE.computeIfAbsent(clazz, ReflectionColumnFieldCache::extractFields);
    }

    // Metodo privado que recibe una clase y extrae los campos que estan anotados con la anotacion @Column
    private static Map<String, Field> extractFields(Class<?> clazz) {
        // Crea un nuevo mapa para almacenar los campos.
        Map<String, Field> fields = new HashMap<>();

        // Itera sobre todos los campos declarados de la clase (incluye los privados)
        for (Field field : clazz.getDeclaredFields()) {
            // Verifica si el campo tiene la anotacion @Column
            if (field.isAnnotationPresent(Column.class)) {
                // Si tiene la anotacion @Column, obtiene la anotacion
                Column column = field.getAnnotation(Column.class);
                // Hace el campo accesible aunque sea privado
                field.setAccessible(true);
                // Almacena el campo en el mapa, usando el nombre de la columna (obtenido de la anotacion @Column) como clave
                fields.put(column.name(), field);
            }
        }
        // Devuelve el mapa de campos extraidos
        return fields;
    }
}
