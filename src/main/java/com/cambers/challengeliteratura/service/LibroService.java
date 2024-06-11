package com.cambers.challengeliteratura.service;

import com.cambers.challengeliteratura.api.GutendexApi;
import com.cambers.challengeliteratura.model.Lenguaje;
import com.cambers.challengeliteratura.model.dto.FormatsLibroDTO;
import com.cambers.challengeliteratura.model.dto.LibroDTO;
import com.cambers.challengeliteratura.model.dto.RespuestaLibroDTO;
import com.cambers.challengeliteratura.model.entity.Autor;
import com.cambers.challengeliteratura.model.entity.Libro;
import com.cambers.challengeliteratura.repository.AutorRepository;
import com.cambers.challengeliteratura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibroService {
    private final String API_SEARCH_BOOK = "https://gutendex.com/books?search=";
    private ConvertirDatos conversor = new ConvertirDatos();

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public List<Libro> obtenerLibros(){
        return libroRepository.findAll();
    }

    public List<Libro> buscarLibro(String tituloLibro){
        GutendexApi response = new GutendexApi();
        var json = response.obtenerDatos(API_SEARCH_BOOK+tituloLibro.replace(" ","%20"));

        RespuestaLibroDTO libroDTO = conversor.obtenerDatos(json, RespuestaLibroDTO.class);
        return guardarLibro(libroDTO);
    }

    private List<Libro> guardarLibro(RespuestaLibroDTO libroDTO) {
        List<Libro> librosGuardados = new ArrayList<>();
        List<Libro> librosEncontrados = new ArrayList<>();

        libroDTO.resultados().forEach(dto -> {
            Long libroId = dto.id();

            // Verificar si el libro ya existe
            Optional<Libro> optionalLibro = libroRepository.findById(libroId);

            // Si el libro no existe, crear un nuevo libro y guardarlo
            if (!optionalLibro.isPresent()) {
                Libro libro = new Libro();
                libro.setId(libroId);
                libro.setTitulo(dto.titulo());
                libro.setLenguajes(dto.lenguajes());
                libro.setDownloadCount(dto.cantidadDesargas());
                // Obtener la URL de la imagen de la portada y link del libro para ver y descargar
                FormatsLibroDTO formatos = dto.formats();

                // Verificar si el formato del libro no es nulo ni vacío
                if (formatos != null && isNotEmpty(formatos.imageUrl()) && isNotEmpty(formatos.bookLink()) && isNotEmpty(formatos.donwloadBookLink())) {
                    Map<String, String> map = new HashMap<>();
                    map.put("imageUrl", formatos.imageUrl());
                    map.put("bookLink", formatos.bookLink());
                    map.put("downloadBookLink", formatos.donwloadBookLink());
                    libro.setFormatos(map);

                    List<Autor> autores = dto.autores().stream()
                            .map(autorDTO -> {
                                Optional<Autor> optionalAutor = autorRepository.findByName(autorDTO.name());
                                return optionalAutor.orElseGet(() -> {
                                    Autor autor = new Autor();
                                    autor.setName(autorDTO.name());
                                    autor.setBirthYear(autorDTO.birthYear());
                                    autor.setDeathYear(autorDTO.deathYear());
                                    return autorRepository.save(autor);
                                });
                            })
                            .collect(Collectors.toList());

                    libro.setAutores(autores);
                    librosGuardados.add(libro);
                    librosEncontrados.add(libro);
                } else {
                    // Aquí puedes manejar la situación de que el formato esté vacío o nulo si es necesario
                    // Por ejemplo, podrías registrar un mensaje de error o simplemente no agregar el libro
                }
            } else {
                librosEncontrados.add(optionalLibro.get()); // Agregar libros existentes a la lista resultante
            }
        });

        // Guardar todos los libros nuevos
        libroRepository.saveAll(librosGuardados);

        // Devolver todos los libros encontrados, tanto los guardados como los que ya existían
        return librosEncontrados;
    }

    // Método de utilidad para verificar si una cadena no es nula ni vacía
    private boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public List<Libro> buscarLibroPorIdioma(Lenguaje lenguaje){
        return libroRepository.findLibrosByLenguajesContaining(lenguaje);
    }

}
