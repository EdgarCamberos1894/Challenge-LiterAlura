package com.cambers.challengeliteratura.repository;

import com.cambers.challengeliteratura.model.entity.Autor;
import com.cambers.challengeliteratura.model.entity.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AutorRepository extends JpaRepository<Autor,Long> {
    //Encuentra autores por su nombre
    Optional<Autor> findByName(String name);

    //Encuentra autores vivos en determinado año andjuntado con sus libros
    @EntityGraph(attributePaths = "libros")
    @Query("SELECT a FROM Autor a WHERE a.birthYear <= :fecha AND  a.deathYear >= :fecha")
    List<Autor> encuentrAutoresVivosEnFecha(@Param("fecha") int fecha);

    //Encuentra todos los autores con sus libros adjuntados
    @Query("SELECT a FROM Autor a")
    @EntityGraph(attributePaths = "libros")
    List<Autor> findAllAutoresWithLibros();

    //Encuentra Autores por id con los libros anjuntados
    @Query("SELECT a FROM Autor a WHERE a.id = :idAutor")
    @EntityGraph(attributePaths = "libros")
    List<Autor> findByIdWithLibros(@Param("idAutor") int idAutor);
}
