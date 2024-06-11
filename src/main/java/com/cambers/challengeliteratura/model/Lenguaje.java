package com.cambers.challengeliteratura.model;


import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public enum Lenguaje {
    Español("es", "Español"),
    Inglés("en", "Inglés"),
    Francés("fr", "Francés"),
    Tagalog("tl", "Tagalog"),
    Alemán("de", "Alemán"),
    Portugués("pt", "Portugués"),
    Neerlandés("nl", "Neerlandés"),
    Árabe("ar", "Árabe");

    private final String code;
    private final String nombre;

    private Lenguaje(String code, String nombre) {
        this.code = code;
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public String getCode(){
        return code;
    }

    @JsonCreator
    public static Lenguaje fromCode(String code) {
        return lookup.get(code);
    }

    // Mapa para buscar un lenguaje por su código
    private static final Map<String, Lenguaje> lookup = new HashMap<>();

    // Inicialización estática del mapa
    static {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            lookup.put(lenguaje.code, lenguaje);
        }
    }


    public static Lenguaje fromNombre(String nombre) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.nombre.equalsIgnoreCase(nombre)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("No se encontró un lenguaje con el nombre: " + nombre);
    }
}