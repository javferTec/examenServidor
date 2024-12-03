package com.fpmislata.basespring.persistence.dao.db.jdbc.utils.sql;

import com.fpmislata.basespring.common.annotation.persistence.ManyToMany;
import com.fpmislata.basespring.common.annotation.persistence.OneToMany;
import com.fpmislata.basespring.persistence.dao.db.jdbc.utils.metadata.EntityMetadataExtractor;

public class SqlBuilderRelation<T> {

    private final EntityMetadataExtractor<T> metadata;

    public SqlBuilderRelation(EntityMetadataExtractor<T> metadata) {
        this.metadata = metadata;
    }


    // Metodo para construir la consulta de una relacion OneToMany
    public String buildOneToManyQuery(OneToMany annotation) {
        return String.format("SELECT * FROM %s WHERE %s = ?",
                annotation.targetEntity().getSimpleName().toLowerCase(), annotation.mappedBy());
    }

    // Metodo para construir la consulta de una relacion ManyToMany
    public String buildManyToManyQuery(ManyToMany annotation) {
        return String.format("SELECT t.* FROM %s t INNER JOIN %s jt ON t.id = jt.%s WHERE jt.%s = ?",
                metadata.getTableName(annotation.targetEntity()), annotation.joinTable(), annotation.inverseJoinColumn(), annotation.joinColumn());
    }

}
