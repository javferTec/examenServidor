package com.fpmislata.basespring.persistence.dao.db.jdbc.utils.sql;

import java.util.Map;
import java.util.stream.Collectors;

public class SqlBuilderOperation {

    // Construye un SQL de inserción basado en el nombre de la tabla y los valores proporcionados
    public String buildInsertSql(String tableName, Map<String, Object> values) {
        String columns = String.join(", ", values.keySet()); // Obtiene los nombres de las columnas
        String placeholders = values.keySet().stream().map(key -> ":" + key).collect(Collectors.joining(", ")); // Genera los marcadores de posición
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")"; // Retorna el SQL de inserción
    }

    // Construye un SQL de actualización basado en el nombre de la tabla, los valores y la clave primaria
    public String buildUpdateSql(String tableName, Map<String, Object> values, String primaryKey) {
        String setClause = values.keySet().stream()
                .filter(key -> !key.equals(primaryKey)) // Excluye la clave primaria de la cláusula SET
                .map(key -> key + " = :" + key) // Genera las asignaciones columna = :valor
                .collect(Collectors.joining(", "));
        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + primaryKey + " = :" + primaryKey; // Retorna el SQL de actualización
    }

    // Construye un SQL con criterios WHERE basado en un SQL base y los criterios proporcionados
    public String buildSqlWithCriteria(String baseSql, Map<String, Object> criteria) {
        if (criteria.isEmpty()) return baseSql; // Si no hay criterios, retorna el SQL base
        String whereClause = criteria.keySet().stream()
                .map(key -> key + " = :" + key) // Genera las condiciones WHERE clave = :valor
                .collect(Collectors.joining(" AND "));
        return baseSql + " WHERE " + whereClause; // Retorna el SQL completo con la cláusula WHERE
    }

}
