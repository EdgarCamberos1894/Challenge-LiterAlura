package com.cambers.challengeliteratura.service;

import com.cambers.challengeliteratura.model.entity.Autor;
import com.cambers.challengeliteratura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<Map<String, Object>> getAllAutoresConLibros(){
        List<Autor> autores = autorRepository.findAllAutoresWithLibros();
        return construirListAutorConLibros(autores);
    }

    public List<Map<String, Object>> encuentraAutoresVivosEnFecha(int fecha) {
        List<Autor> autores = autorRepository.encuentrAutoresVivosEnFecha(fecha);
        return construirListAutorConLibros(autores);
    }

    public List<Map<String, Object>> getAutorPorIDConLibros(int idAutor) {
        List<Autor> autores = autorRepository.findByIdWithLibros(idAutor);
        return construirListAutorConLibros(autores);
    }

    public List<Map<String, Object>> construirListAutorConLibros(List<Autor> autores){
        return autores.stream().map(autor -> Map.of(
                "id", autor.getId(),
                "name", autor.getName(),
                "birthYear", autor.getBirthYear(),
                "deathYear", autor.getDeathYear(),
                "libros", autor.getLibros().stream().map(
                        libro -> Map.of(
                                "id", libro.getId(),
                                "titulo", libro.getTitulo(),
                                "formatos",libro.getFormatos(),
                                "downloadCount",libro.getDownloadCount(),
                                "lenguajes",libro.getLenguajes(),
                                "autores",libro.getAutores()
                        )).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }
}
