package com.fpmislata.basespring.controller.user.userModel.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpmislata.basespring.controller.common.entity.model.actor.ActorCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieUserDetail {
    private String title;
    private Integer year;
    private String director;
    @JsonProperty("actors")
    private List<ActorCollection> actors;
}
