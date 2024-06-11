package com.cambers.challengeliteratura.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "autores")
@Data
@NoArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer birthYear;

    private Integer deathYear;

    @ManyToMany(mappedBy = "autores")
    @JsonBackReference
    private List<Libro> libros;

}

