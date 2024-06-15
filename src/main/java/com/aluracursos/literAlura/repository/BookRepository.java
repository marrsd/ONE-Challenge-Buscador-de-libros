package com.aluracursos.literAlura.repository;


import com.aluracursos.literAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM books WHERE :language = ANY(languages)", nativeQuery = true)
    Optional<List<Book>> findByLanguagesContaining(String language);



}
