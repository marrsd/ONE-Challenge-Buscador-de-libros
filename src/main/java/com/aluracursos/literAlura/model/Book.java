package com.aluracursos.literAlura.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Author> authors;

    private List<String> languages;

    private Integer downloads;

   @Override
    public String toString() {

       System.out.println();

        StringBuilder names = new StringBuilder();
        StringBuilder language = new StringBuilder();

        for (int i = 0; i < authors.size(); i++) {
            if (authors.size() > 0) {
                if(i == authors.size() - 1) {
                    names.append(authors.get(i).getName());
                } else {
                    names.append(authors.get(i).getName()).append(" - ");
                }
            }
        }

        for (int i = 0; i < languages.size(); i++) {
            if (languages.size() > 0) {
                if(i == languages.size() - 1) {
                    language.append(languages.get(i));
                } else {
                    language.append(languages.get(i)).append(" - ");
                }
            }
        }
        return """
                ********************** Libro **********************
                    Titulo: %s
                    Autores: %s
                    Idioma: %s
                    Número de descargas: %d
                ***************************************************
                """.formatted(title, names, language, downloads);
    }
}