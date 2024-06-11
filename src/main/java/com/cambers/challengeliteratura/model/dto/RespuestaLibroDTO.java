package com.cambers.challengeliteratura.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaLibroDTO(@JsonProperty("results") List<LibroDTO> resultados) {
}




