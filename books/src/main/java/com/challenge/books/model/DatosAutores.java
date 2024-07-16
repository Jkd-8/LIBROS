package com.challenge.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutores(
        @JsonAlias ("name") String nombreDelAutor,
        @JsonAlias ("birth_year") String fechaDeNacimiento,
        @JsonAlias("death_year") String añoDeFallecimiento

) {
}
