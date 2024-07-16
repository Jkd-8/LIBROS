package com.challenge.books.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "Libros")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String nombreAutor;
    private String titulo;
    private String lenguajes;

    @ManyToOne
    private Autores autor;

    private Long idAutor;

    public Books(){

    }

    public Books(DatosBooks datosBooks, String nombreAutor,Long idAutor){
        this.titulo = datosBooks.titulo();
        this.nombreAutor = nombreAutor;
        this.idAutor = idAutor;

        this.lenguajes = datosBooks.lenguajes().get(0);

    }




    @Override
    public String toString() {
        return "Books{" +
                "Id=" + Id +
                ", nombreAutor='" + nombreAutor + '\'' +
                ", titulo='" + titulo + '\'' +
                ", lenguajes='" + lenguajes + '\'' +
                ", idAutor=" + idAutor +
                '}';
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String lenguajes) {
        this.lenguajes = lenguajes;
    }

}
