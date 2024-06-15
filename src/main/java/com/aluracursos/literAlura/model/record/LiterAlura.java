package com.aluracursos.literAlura.model.record;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LiterAlura(@JsonAlias("results") List<BookRecord> books) {}
