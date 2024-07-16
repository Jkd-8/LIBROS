package com.challenge.books.principal;

import com.challenge.books.model.*;
import com.challenge.books.repositorio.RepositoryAutor;
import com.challenge.books.repositorio.RepositoryBooks;
import com.challenge.books.servicios.Api;
import com.challenge.books.servicios.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;



public class Principal {

    public Scanner in = new Scanner(System.in);
    public Api consumoapi = new Api();
    public String URL_BASE = "https://gutendex.com/books/";
    public ConvierteDatos conversor = new ConvierteDatos();
    private  RepositoryAutor autorRepositorio;
    private RepositoryBooks libroRepositorio;
    private boolean verificador = false;
    private  String opcion;
    private String respuesta;
    private List<Books> librosBuscados = new ArrayList<>();

    public Principal(RepositoryAutor autorRepositorio, RepositoryBooks libroRepositorio){
        this.autorRepositorio = autorRepositorio;
        this.libroRepositorio = libroRepositorio;
    }

    private void leerLibro (Books libro){
        System.out.println("Libro mostrado ");
        System.out.println("Titulo: "+ libro.getTitulo());
        System.out.println("Autor:" + libro.getNombreAutor());
        System.out.println("Idioma: " + libro.getLenguajes());
    }

    private void datosAutor (Autores autores){
        System.out.println("Autor: " + autores.getNombreDelAutor());
        System.out.println("Fecha de nacimiento: " + autores.getFechaDeNacimiento());
        System.out.println("Fecha de muerte: " + autores.getAñoDeFallecimiento());
//        List<String> libros = autores.getLibros().stream()
//                .map(l -> l.getTitulo())
////                .collect(Collectors.toList());
//        System.out.println("Libros: " + libros + "\n");
    }


    public void muestraDeMenu() {

         var menu = """
                            \n**************\n

                    1. Busqueda de libro por titulo
                    2. Lista de todos los libros registrados
                    3. Lista de Autores Registrados
                    4. Busqueda por autor
                    5. Lista de Autores vivos en determinado año
                    6. Lista de libros por Idioma
                    0. Salir del Menú
                    """;
         System.out.println(menu);

        do {
         opcion = in.nextLine();

        do {

            verificador = false;
            try {
                Integer.parseInt(opcion);

            }catch (NumberFormatException e ){
                System.out.println(menu);
                verificador= true;
                opcion = in.nextLine();
            }

            } while (verificador == true);



                switch (Integer.parseInt(opcion)){
                    case 1:
                        buscarLibros();
                        break;
                    case 2:
                        todosLosLibrosRegistrados();
                        break;
                    case 3:
                        todosLosAutoresRegistrados();
                        break;
                    case 4:
                        busquedaPorAutor();
                        break;

                    case 5:
                        listaDeAutoresVivos();
                        break;
                    case 6:
                        librosPorLenguaje();
                        break;

                    case 0:
                        System.out.println("Saliendo del menú");
                        break;
                    default:
                        System.out.println("Opcion Valida");
                        break;


                }
            System.out.println(menu);

        } while(Integer.parseInt(opcion) > 0);
                in.close();

    }


    public List<String> listaDeLenguajes(){
        System.out.println("Idiomas de libros seleccionados: ");
        String chainLenguajes = libroRepositorio.lenguajesDeIdiomas().toString().replaceAll("\\[|\\]", "");
        List<String> idiomasEnTotal = new ArrayList<String>();
        for (short i = 0; i < chainLenguajes.split(",").length; i ++){
            idiomasEnTotal.add(chainLenguajes.split(",")[i].trim());

        }
        List<String> idiomasPrimarios = new ArrayList<String>();
        idiomasPrimarios.stream().distinct().forEach(i -> idiomasPrimarios.add(i));
        return idiomasPrimarios;

    }

 public void buscarLibros() {
     System.out.println("Escribe el nombre del libro que deseas buscar ");
     var nameLibro = in.nextLine();
     var json = consumoapi.obtenerDatos(URL_BASE + "?search=" + nameLibro.replaceAll(" ", "+"));
     List<DatosBooks> libros = conversor.obtenerDatos(json, Datos.class).resultados();
     Optional<DatosBooks> firstLibro = libros.stream()
             .filter(l -> l.titulo().toLowerCase().contains(nameLibro.toLowerCase()))
             .findFirst();
     if (firstLibro.isPresent()) {
         Autores autor = new Autores(firstLibro.get().autor().get(0));
         if(autorRepositorio.findByNombreDelAutorIgnoreCase(autor.getNombreDelAutor())==null){
             autorRepositorio.save(autor);
         }
         Books guardar = new Books(firstLibro.get(), autor.getNombreDelAutor(),autor.getId());
         libroRepositorio.save(guardar);
         System.out.println("El resultado encontrado es: ");
         leerLibro(guardar);


     } else {
         System.out.println("No se encontro ningun libro");
     }
 }

    public void todosLosLibrosRegistrados() {
       librosBuscados = libroRepositorio.findAll();
        if (librosBuscados.size() > 0){
            for (int i = 0; i< librosBuscados.size();i++){
                leerLibro(librosBuscados.get(i));
            }
//            leerLibro(librosBuscados.get(0));
//            librosBuscados.forEach(System.out::println);
        }else{
            System.out.println("No hay libros registrados aun...");
        }

    }

    public void busquedaPorAutor() {
        System.out.println("Ingrese el nombre de algun autor que desea buscar: ");
        respuesta = in.nextLine();
        String json = consumoapi.obtenerDatos(URL_BASE + "?search=" + respuesta.replaceAll(" ", "+"));
//        var resultado = conversor.obtenerDatos(json, Datos.class);
        List<DatosBooks> librosDelAutor = conversor.obtenerDatos(json, Datos.class).resultados();
        if (librosDelAutor.isEmpty()) {
            System.out.println("No se han encontrado resultados de este autor...");
        } else {
            Books temp = new Books();
            Autores autor = new Autores();
            for (int a = 0; a < librosDelAutor.size(); a++) {
                temp = new Books(librosDelAutor.get(a),respuesta,autor.getId());
                leerLibro(temp);
            }

        }
    }
    private void todosLosAutoresRegistrados() {
        List<Autores> autoresRegistrados = autorRepositorio.findAll();
        for (int i = 0; i < autoresRegistrados.size() ; i++) {
            datosAutor(autoresRegistrados.get(i));

        }
//        autoresRegistrados.stream()
//                .sorted((autor1 , autor2)-> autor1.getNombreDelAutor().compareTo(autor2.getNombreDelAutor()))
//                .forEach(System.out::println);



    }

    public void listaDeAutoresVivos() {

        List<Autores> autoress = new ArrayList<Autores>();
        if (autorRepositorio.findAll().size() <= 0) {
            System.out.println("No hay autores por mostrar");
        } else {

            System.out.println("Ingresa un año en el que deseas consultar escritores vivos: ");
            respuesta = in.nextLine();
            do {
                verificador = false;
                try {
                    Integer.parseInt(respuesta);
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un año valido");
                    verificador = true;
                    respuesta = in.nextLine();

                }
            } while (verificador == true);
            System.out.println("Autores con vida en este año: ");
            autoress = autorRepositorio.findAll();
            if (autoress.isEmpty()) {
                System.out.println("No se encontro ningun dato en este año...");
            } else {
                autoress.stream().distinct().filter(a ->
                        a.getAñoDeFallecimiento() >= Integer.parseInt(respuesta)).forEach(autores -> System.out.println(autores.getNombreDelAutor()));
            }
            autoress.clear();
        }
    }

    private void librosPorLenguaje() {
        String idiomas;
        System.out.println("""
                1. Español
                2. Ingles
                3. Frances


                """);
        var opcion = in.nextInt();
        in.nextLine();
        if (opcion == 1) {
            idiomas = "es";
        } else if (opcion == 2) {
            idiomas = "en";
        } else if (opcion == 3) {
            idiomas = "fr";
        } else {
            idiomas = null;
            System.out.println("Opcion no valida");
        }
        List<Books> librosPorIdioma = libroRepositorio.findAll();
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se han encontrado libros registrados ");
        } else {

            if (!librosPorIdioma.stream().noneMatch( i-> i.getLenguajes().equalsIgnoreCase(idiomas))){
                librosPorIdioma.stream().distinct().filter(i ->
                        i.getLenguajes().equalsIgnoreCase(idiomas)).forEach(librosIdioma -> System.out.println(librosIdioma.getTitulo()));

            }else{
                System.out.println("No se han encontrado idiomas para ese idioma... ");

            }

        }
    }
}





