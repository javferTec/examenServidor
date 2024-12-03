package com.fpmislata.basespring.domain.model;

import com.fpmislata.basespring.common.annotation.persistence.Column;
import com.fpmislata.basespring.common.annotation.persistence.PrimaryKey;
import com.fpmislata.basespring.common.annotation.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "actors")
public class Actor {
    @PrimaryKey
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthYear")
    private Integer birthYear;

    @Column(name = "deathYear")
    private Integer deathYear;
}
