package com.challenge.books.repositorio;

import com.challenge.books.model.Autores;
import com.challenge.books.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface RepositoryBooks extends JpaRepository<Books,Long> {
    @Query("SELECT DISTINCT lenguajes FROM Books")
    List<String> lenguajesDeIdiomas();

}
