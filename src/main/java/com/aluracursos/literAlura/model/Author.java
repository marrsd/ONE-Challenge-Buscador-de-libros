package com.aluracursos.literAlura.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {}

    public Author(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    @Override
    public String toString() {
        StringBuilder allBooks = new StringBuilder();

        for (int i = 0; i < books.size(); i++) {
            if (books.size() > 0) {
                if(i == books.size() - 1) {
                    allBooks.append(books.get(i).getTitle());
                } else {
                    allBooks.append(books.get(i).getTitle()).append(" - ");
                }
            }
        }

        return """
               ********************** Autor **********************
               Autor: %s
               Año de nacimiento: %s
               Año de muerte: %s
               Libros: %s
               ***************************************************
               """.formatted(name, birthYear, deathYear, allBooks);
    }
}
