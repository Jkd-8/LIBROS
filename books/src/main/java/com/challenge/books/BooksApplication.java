package com.challenge.books;

import com.challenge.books.principal.Principal;
import com.challenge.books.repositorio.RepositoryAutor;
import com.challenge.books.repositorio.RepositoryBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BooksApplication implements CommandLineRunner {
	@Autowired
	private RepositoryAutor autorRepositorio;
	@Autowired
	private RepositoryBooks libroRepositorio;


	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}
@Override
	public void run(String... args) throws Exception{
		Principal principal = new Principal(autorRepositorio, libroRepositorio);
		principal.muestraDeMenu();
	}

}
