package com.fpmislata.basespring.common.annotation.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneToOne {
    Class<?> targetEntity(); // La clase de la entidad relacionada

    String joinColumn(); // columna de unión en la tabla principal
}
