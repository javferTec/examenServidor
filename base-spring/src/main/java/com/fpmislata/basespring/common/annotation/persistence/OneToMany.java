package com.fpmislata.basespring.common.annotation.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneToMany {
    Class<?> targetEntity(); // La clase de la entidad relacionada

    String mappedBy(); // columna que referencia la tabla principal
}
