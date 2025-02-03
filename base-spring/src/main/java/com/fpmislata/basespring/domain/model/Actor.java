package com.fpmislata.basespring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    private Integer id;
    private String name;
    private Integer birth_year;
    private Integer death_year;
}
