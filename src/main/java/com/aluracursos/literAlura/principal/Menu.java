package com.aluracursos.literAlura.principal;

import com.aluracursos.literAlura.model.Author;
import com.aluracursos.literAlura.model.Book;
import com.aluracursos.literAlura.model.record.LiterAlura;
import com.aluracursos.literAlura.repository.AuthorRepository;
import com.aluracursos.literAlura.repository.BookRepository;
import com.aluracursos.literAlura.service.GutendexApi;
import com.aluracursos.literAlura.util.ConvertData;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    private BookRepository bookRepository;

    private AuthorRepository authorRepository;
    private Scanner sc = new Scanner(System.in);
    private GutendexApi gutendexApi = new GutendexApi();
    private ConvertData conversor = new ConvertData();
    Map<String, String> opciones = new HashMap<>();

    public Menu(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        var opcion = -1;

        var menu = """
                    Marque el número de la opción que desea ejecutar:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir                    
                    """;

        opciones.put("1", "Ingrese el nombre del libro que desea buscar: ");
        opciones.put("2", "Ingrese el idioma para buscar los libros: ");
        opciones.put("4", "Ingrese el año de nacimiento para obtener información de los autores: ");
        opciones.put("5", "Ingrese el idioma para buscar los libros: \n\t es - Español \n\t en - Inglés \n\t fr - Francés \n\t pt - Portugués");

        while(opcion != 0) {
            System.out.print(menu);
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    getAllBooks();
                    break;
                case 3:
                    getAllAuthors();
                    break;
                case 4:
                    getAuthorsByYear();
                    break;
                case 5:
                    searchBooksByLanguages();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void searchBooksByLanguages() {
        System.out.println(opciones.get("5"));
        String languageSelected = sc.nextLine();

        var booksByLanguage = bookRepository.findByLanguagesContaining(languageSelected);

        if (booksByLanguage.isPresent()) {
            booksByLanguage.get().stream().forEach(System.out::println);
        } else {
            System.out.println("No tienes libros registrados en el idioma seleccionado");
        }
    }

    private void getAuthorsByYear() {
        System.out.println(opciones.get("4"));
        String year = sc.nextLine();
        Optional<List<Author>> authors = authorRepository.findAllByBirthYear(Integer.valueOf(year));
        if(authors.isPresent()) {
            authors.get().stream().forEach(System.out::println);
        } else {
            System.out.println("No tienes libros registrados con autores nacidos en " + year);
        }
    }

    private void getAllAuthors() {
        List<Author> allAuthors = authorRepository.findAll();
        allAuthors.stream().sorted(Comparator.comparing(Author::getName)).forEach(System.out::println);
    }

    private void getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        allBooks.stream().sorted(Comparator.comparing(Book::getTitle)).forEach(System.out::println);
    }

    private void searchBookByTitle() {
        System.out.print(opciones.get("1"));
        var title = sc.nextLine();
        getBook(title);
    }

    @Transactional
    private void getBook(String title) {

        String titleFormatted = title.replaceAll(" ", "%20");

        try {
            var data = gutendexApi.getBooks("https://gutendex.com/books/?search=" + titleFormatted);

            LiterAlura literAlura = conversor.convertData(data, LiterAlura.class);
            var info = literAlura.books().stream().findAny().get();

            var authorsData = info.authors().stream()
                    .map(a -> new Author(a.name(), a.birthYear(), a.deathYear()))
                    .collect(Collectors.toList());

            Book book = new Book();

            book.setTitle(info.title());
            book.setAuthors(authorsData);
            book.setLanguages(info.languages());
            book.setDownloads(info.downloads());

            System.out.println(book);

            authorRepository.saveAll(authorsData);
            bookRepository.save(book);
        }
        catch(NoSuchElementException e) {
            System.out.println("Libro no encontrado");
        }
        catch(DataIntegrityViolationException e) {
            System.out.println("El libro ya existe, elige la opción 2 para obtener información del libro que buscas");
        }
    }
}
