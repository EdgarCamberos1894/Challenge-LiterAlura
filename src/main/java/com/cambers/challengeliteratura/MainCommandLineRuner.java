package com.cambers.challengeliteratura;

import com.cambers.challengeliteratura.model.entity.Libro;
import com.cambers.challengeliteratura.service.AutorService;
import com.cambers.challengeliteratura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.cambers.challengeliteratura.model.entity.Autor;
import com.cambers.challengeliteratura.model.Lenguaje;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MainCommandLineRuner implements CommandLineRunner {

    private final AutorService autorService;
    private final LibroService libroService;

    Scanner scanner = new Scanner(System.in);

    public MainCommandLineRuner(AutorService autorService, LibroService libroService) {
        this.autorService = autorService;
        this.libroService = libroService;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean repetir = true;

        while (repetir) {
            System.out.print(
                """
                --------------------------------------------
                             Elije una opción
                       
                1.- Buscar libro por titulo
                2.- Listar libros Registrados
                3.- Listar autores registrados
                4.- Listar autores vivos en determinado año
                5.- Listar libros por idioma
                6.- Salir
                ----------------------------------------------
                """
            );

            int opcion = elegirOpcion(6);
            switch (opcion) {
                case 1 -> buscarLibros();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> autoresVivosEnElAño();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> repetir=false; //opcion para salir
            }
            if(opcion!=6) {
                System.out.println(
                    """
                    ---------------------------------------------
                            ¿Desea realizar otra operación?
                               1.- Si          2.- No
                    ---------------------------------------------
                    """
                );
                opcion = elegirOpcion(2);
                if (opcion == 2) {
                    repetir = false;
                }
            }
        }
    }

    private void buscarLibros() {
        System.out.println(
            """
            ---------------------------------------------
                    Ingresa el Titulo del Libro
            ---------------------------------------------
            """
        );
        String titulo = scanner.nextLine() ;
        List<Libro> libros = libroService.buscarLibro(titulo);

        if (!libros.isEmpty()) {
            libros.stream().forEach(libro -> {
                System.out.printf(
                    """
                    -------------------- LIBRO -----------------
                    Titulo: %s
                    Autor: %s
                    Idioma: %s
                    Número de descargas: %d
                    --------------------------------------------
                    """, libro.getTitulo(),
                    libro.getAutores().stream().map(Autor::getName).collect(Collectors.joining(", ")),
                    libro.getLenguajes().stream().map(Lenguaje::getNombre).collect(Collectors.joining(", ")),
                    libro.getDownloadCount()
                );
            });
        }
        else{
            System.out.println(
                """
                ----------------------------------------------
                   No se encontraron libros con ese título.
                ----------------------------------------------
                """
            );
        }


    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroService.obtenerLibros();
        mostrarMensajeDeLibros(libros);
    }

    private void mostrarMensajeDeLibros(List<Libro> libros) {
        libros.forEach(libro ->
            System.out.printf(
                """
                -------------------- LIBRO -----------------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d
                --------------------------------------------\n
                """,libro.getTitulo(),
                libro.getAutores().stream().map(Autor::getName).collect(Collectors.joining(", ")),
                libro.getLenguajes().stream().map(Lenguaje::getNombre).collect(Collectors.joining(",")),
                libro.getDownloadCount()
            )
        );
    }

    private void listarAutoresRegistrados() {
        List<Map<String, Object>> autores = autorService.getAllAutoresConLibros();
        mostrarMensajeDeAutores(autores);
    }

    private void autoresVivosEnElAño() {
        System.out.println(
            """
            ------------------------------------------
               Ingresa el año que quieres consultar
            ------------------------------------------
            """
        );
        String año = scanner.nextLine();
        if(validarAño(año)){
            List<Map<String, Object>> autores = autorService.encuentraAutoresVivosEnFecha(Integer.parseInt(año));
            if(!autores.isEmpty()){
                mostrarMensajeDeAutores(autores);
            }
            else{
                System.out.println(
                    """
                    ----------------------------------------------------------------
                      No se encontraron registros de Autores vivos durante ese año
                    ----------------------------------------------------------------
                    """);
            }
        }
    }

    private void mostrarMensajeDeAutores(List<Map<String, Object>> autores) {
        autores.forEach(autor -> {
            List<Map<String, Object>> libros = (List<Map<String, Object>>) autor.get("libros");
            System.out.printf(
                """
                ----------------- Autor ---------------
                Nombre: %s
                Fecha de nacimiento: %s
                Fecha de fallecimiento: %s
                Libros: [%s]
                ---------------------------------------\n
                """,autor.get("name"),
                    autor.get("birthYear"),
                    autor.get("deathYear"),
                    libros.stream().map(libro -> libro.get("titulo").toString()).collect(Collectors.joining(", "))
            );
        });
    }

    private void listarLibrosPorIdioma() {
        Map<String, String>[] lenguajesArray = Arrays.stream(Lenguaje.values()).map(lenguaje -> Map.of(lenguaje.getCode(), lenguaje.getNombre())).toArray(Map[]::new);

        String listaLenguajes = IntStream.range(0, lenguajesArray.length)
                .mapToObj(i -> (i + 1) + ".- " + lenguajesArray[i].values().iterator().next())
                .collect(Collectors.joining("\n"));

        System.out.printf(
            """
            ------------------------------------------
                       Elije una opción
                       
            %s
            ------------------------------------------
            """,listaLenguajes
        );
        int opcion = elegirOpcion(Lenguaje.values().length)-1;
        String codigoIdioma = lenguajesArray[opcion].values().iterator().next();
        Lenguaje lenguaje = Lenguaje.fromNombre(codigoIdioma);
        List<Libro> libros = libroService.buscarLibroPorIdioma(lenguaje);
        if(!libros.isEmpty()) {
            mostrarMensajeDeLibros(libros);
        }
        else{
            System.out.println(
                """
                --------------------------------------------------
                    No hay libros registrados en ese lenguaje
                --------------------------------------------------
                """
            );
        }


    }

    public int elegirOpcion(int numeroOpciones) {
        String opcion = scanner.nextLine();
        while (true) {
            try {
                int numero = Integer.parseInt(opcion);
                if (numero >= 1 && numero <= numeroOpciones) {
                    return numero;
                }
                throw new IllegalArgumentException() ;
            } catch (IllegalArgumentException e) {
                System.out.printf(
                """
                -----------------------------------------------------
                                   Entrada inválida.
                   Por favor, ingresa un número entero entre 1 y %d
                -----------------------------------------------------
                """,numeroOpciones);
                opcion = scanner.nextLine();
            }
        }
    }

    public boolean validarAño(String año) {

        int añoActual = Year.now().getValue();

        while (true) {
            try {
                int fecha = Integer.parseInt(año);
                if(fecha>= 0 && fecha <= añoActual) {
                    return true;
                }
                throw new IllegalArgumentException() ;
            } catch (IllegalArgumentException e) {
                System.out.printf(
                        """
                        ----------------------------------------------------------
                            Por favor, ingresa un año valido menos o iual a %d
                        ----------------------------------------------------------
                        """
                        ,añoActual);
                año = scanner.nextLine() ;
            }
        }
    }
}
