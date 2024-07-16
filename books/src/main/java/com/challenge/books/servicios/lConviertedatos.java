package com.challenge.books.servicios;

public interface lConviertedatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
