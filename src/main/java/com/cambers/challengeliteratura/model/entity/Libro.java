package com.cambers.challengeliteratura.model.entity;

import com.cambers.challengeliteratura.model.Lenguaje;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Entity
@Table(name="libros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    private Long id;

    @Column(length = 500) // Define la longitud máxima del título
    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    @JsonManagedReference
    private List <Autor> autores;

    @ElementCollection
    private Map<String, String> formatos;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Lenguaje> lenguajes;

    private Integer downloadCount;



}
