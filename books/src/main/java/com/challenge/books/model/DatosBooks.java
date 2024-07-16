package com.challenge.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record DatosBooks(
        @JsonAlias("title") String titulo,
        @JsonAlias ("languages") List<String> lenguajes,
        @JsonAlias ("authors")List<DatosAutores> autor

        ) {


}
