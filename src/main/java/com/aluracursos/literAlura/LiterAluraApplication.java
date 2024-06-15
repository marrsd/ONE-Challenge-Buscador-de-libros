package com.aluracursos.literAlura;

import com.aluracursos.literAlura.principal.Menu;
import com.aluracursos.literAlura.repository.AuthorRepository;
import com.aluracursos.literAlura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public void run(String... args) throws Exception {
		Menu menu = new Menu(bookRepository, authorRepository);
		menu.showMenu();
	}
}
