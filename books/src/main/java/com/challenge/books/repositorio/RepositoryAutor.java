package com.challenge.books.repositorio;

import com.challenge.books.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepositoryAutor extends JpaRepository<Autores, Long> {
    Autores findByNombreDelAutorIgnoreCase(String nombreDelAutor);

//    List<Autores> findByFechaDeFallecimientoGreaterThan(Integer fechaDeFallecimeinto);
@Query("SELECT a FROM Autores a WHERE a.fechaDeNacimiento <= :year AND :year < a.añoDeFallecimiento OR a.añoDeFallecimiento = 0")
    List<Autores> autorVivosEnCiertoAño(@Param("year") int year);
}
