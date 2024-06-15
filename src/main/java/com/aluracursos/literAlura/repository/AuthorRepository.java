package com.aluracursos.literAlura.repository;


import com.aluracursos.literAlura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<List<Author>> findAllByBirthYear(Integer birthYear);
}
