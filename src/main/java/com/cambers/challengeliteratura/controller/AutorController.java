package com.cambers.challengeliteratura.controller;


import com.cambers.challengeliteratura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("/autores")
    public List<Map<String, Object>> getAutores(){
        return autorService.getAllAutoresConLibros();
    }

    @GetMapping("/autores/fecha/{fecha}")
    public List<Map<String, Object>> getAutorPorFecha(@PathVariable int fecha) {
         return autorService.encuentraAutoresVivosEnFecha(fecha);

    }

    @GetMapping("/autores/{idAutor}/libros")
    public List<Map<String, Object>> obtenerLibrosAutor(@PathVariable int idAutor) {
        return autorService.getAutorPorIDConLibros(idAutor);
    }
}
