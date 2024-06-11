package com.cambers.challengeliteratura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FormatsLibroDTO(
        @JsonAlias("image/jpeg")  String imageUrl,
        @JsonAlias("text/html") String bookLink,
        @JsonAlias("application/octet-stream") String donwloadBookLink
) {
}
