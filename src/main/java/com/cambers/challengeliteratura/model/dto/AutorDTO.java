package com.cambers.challengeliteratura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") int birthYear,
        @JsonAlias("death_year") int deathYear) {
}
