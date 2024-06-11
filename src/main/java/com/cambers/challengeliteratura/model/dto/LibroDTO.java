package com.cambers.challengeliteratura.model.dto;

import com.cambers.challengeliteratura.model.Lenguaje;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorDTO> autores,
        @JsonAlias("formats") FormatsLibroDTO formats,
        @JsonAlias("languages") Set<Lenguaje> lenguajes,
        @JsonAlias("download_count") int cantidadDesargas) {

}
