package com.fpmislata.basespring.domain.model;

import com.fpmislata.basespring.common.annotation.persistence.*;
import com.fpmislata.basespring.common.locale.LanguageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies")
public class Movie {
    @PrimaryKey
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private Integer year;

    @Column(name = "image")
    private String image;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "description")
    private String description;

    @OneToOne(targetEntity = Director.class,
            joinColumn = "director_id")
    private Director director;

    @ManyToMany(targetEntity = Actor.class,
            joinTable = "actors_movies",
            joinColumn = "movie_id",
            inverseJoinColumn = "actor_id")
    private List<Actor> actors;


    public String getName() {
        String language = LanguageUtils.getCurrentLanguage();
        /*if ("en".equals(language)) {
            return titleEn;
        }*/
        return title;
    }

    public String getDescription() {
        String language = LanguageUtils.getCurrentLanguage();
        /*if ("en".equals(language)) {
            return descriptionEn;
        }*/
        return description;
    }

    public void addDirector(Director director) {
        this.director = director;
    }

    public void addActors(Actor actor) {
        this.actors.add(actor);
    }

}
