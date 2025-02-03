package com.fpmislata.basespring.domain.model;

import com.fpmislata.basespring.common.locale.LanguageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private Integer id;
    private String title;
    private Integer year;
    private String image;
    private Integer runtime;
    private String description;
    private Director director;
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
