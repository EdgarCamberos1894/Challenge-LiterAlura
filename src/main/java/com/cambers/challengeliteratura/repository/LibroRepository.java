package com.cambers.challengeliteratura.repository;

import com.cambers.challengeliteratura.model.Lenguaje;
import com.cambers.challengeliteratura.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    //Encuentra todos los libros registrados
    List<Libro> findAll();

    //Encuentra los libros escritos en el lenguaje especificado
    List<Libro> findLibrosByLenguajesContaining(Lenguaje lenguaje);


}
