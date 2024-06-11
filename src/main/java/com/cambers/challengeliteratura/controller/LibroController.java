package com.cambers.challengeliteratura.controller;

import com.cambers.challengeliteratura.model.Lenguaje;
import com.cambers.challengeliteratura.model.entity.Libro;
import com.cambers.challengeliteratura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/libros")
    public List<Libro> getAllLibros() {
        return libroService.obtenerLibros();
    }

    @GetMapping("/libros/{titulo}")
    public List<Libro> getLibrosPorTitulo(@PathVariable String titulo) {
        return libroService.buscarLibro(titulo);
    }

    @GetMapping("/libros/idioma/{idioma}")
    public List<Libro> getLibrosPorIdioma(@PathVariable String idioma) {
        String idiomaDecodificado = URLDecoder.decode(idioma, StandardCharsets.UTF_8);
        Lenguaje lenguaje = Lenguaje.fromNombre(idiomaDecodificado);
        return libroService.buscarLibroPorIdioma(lenguaje);
    }


}
