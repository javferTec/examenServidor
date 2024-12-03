package com.fpmislata.basespring.common.annotation.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToMany {
    Class<?> targetEntity();

    String joinTable();  // tabla de unión

    String joinColumn(); // columna que referencia la tabla principal

    String inverseJoinColumn(); // columna que referencia la tabla relacionada
}