package com.challenge.books.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Autores")
public class Autores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String nombreDelAutor;

    private int fechaDeNacimiento;
    private int añoDeFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Books> libros ;

    public Autores(){

    }

    public Autores(DatosAutores autores) {
        this.nombreDelAutor= autores.nombreDelAutor();
        this.fechaDeNacimiento =  Integer.parseInt(autores.fechaDeNacimiento());
        this.añoDeFallecimiento = Integer.parseInt(autores.añoDeFallecimiento());




    }






    @Override
    public String toString() {
        return "Autores{" +
                "Id=" + Id +
                ", nombreDelAutor='" + nombreDelAutor + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", añoDeFallecimiento=" + añoDeFallecimiento +
                ", libros=" + libros +
                '}';
    }

    public String getNombreDelAutor() {
        return nombreDelAutor;
    }

    public void setNombreDelAutor(String nombreDelAutor) {
        this.nombreDelAutor = nombreDelAutor;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public int getAñoDeFallecimiento() {
        return añoDeFallecimiento;
    }

    public void setAñoDeFallecimiento(Integer añoDeFallecimiento) {
        this.añoDeFallecimiento = añoDeFallecimiento;
    }

    public List<Books> getLibros() {
        return libros;
    }

    public void setLibros(List<Books> libros) {
        this.libros = libros;
    }



}
