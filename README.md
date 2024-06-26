# LiterAlura - Catálogo de Libros

¡Bienvenidos al proyecto LiterAlura! Este es un catálogo de libros desarrollado como parte del desafío de Oracle Next Education y Alura Latam. En este README, detallaré las funcionalidades implementadas y cómo logré completar el proyecto siguiendo las instrucciones proporcionadas. Además, mostraré un extra que añadí: una interfaz FrontEnd.

## Tabla de Contenidos

1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Requisitos del Proyecto](#requisitos-del-proyecto)
3. [Configuración del Entorno](#configuración-del-entorno)
4. [Funcionalidades Implementadas](#funcionalidades-implementadas)
5. [Manejo de Errores](#manejo-de-errores)
6. [Funcionalidades Adicionales](#funcionalidades-adicionales)
7. [Recursos Utilizados](#recursos-utilizados)


## Descripción del Proyecto

LiterAlura es un catálogo de libros en el que los usuarios pueden registrar libros consultando una API y almacenando la información en una base de datos. Luego, los usuarios pueden realizar diversas operaciones con los libros registrados.

## Requisitos del Proyecto

Las funcionalidades básicas que implementé son:

1. Buscar un libro por su título y registrarlo en la base de datos.
2. Listar todos los libros registrados.
3. Listar todos los autores registrados.
4. Listar los autores vivos en un año determinado.
5. Listar los libros por idioma.

## Configuración del Entorno

Para configurar el entorno, seguí estos pasos:

1. **Creación del Proyecto:**
    - Utilicé [Spring Initializr](https://start.spring.io) para crear el proyecto con las siguientes configuraciones:
        - Proyecto: Maven
        - Lenguaje: Java
        - Framework: Spring Boot
        - Packaging: JAR
        - Versión de Java: 17
        - Dependencias: Spring Data JPA, PostgreSQL Driver

2. **Configuración de la Base de Datos:**
    - Instalé PostgreSQL y creé una base de datos llamada `literalura`.
    - Configuré las propiedades de conexión en `application.properties` (${DB_HOST}, ${DB_USER}, ${DB_PASSWORD}).

## Funcionalidades Implementadas

### 1. Buscar y Registrar Libros

Implementé un método para buscar un libro por su título utilizando la API de Gutendex. Si el libro existe, su información se registra en la base de datos. Aseguré que no se pueda insertar el mismo libro dos veces.

### 2. Listar Libros Registrados

Este método lista todos los libros que están registrados en la base de datos.

### 3. Listar Autores Registrados

Implementé un método para listar todos los autores de los libros registrados.

### 4. Listar Autores Vivos en un Año Determinado

Este método lista los autores que estaban vivos en un año específico.

### 5. Listar Libros por Idioma

Los usuarios pueden listar los libros por su idioma.

![Buscar y Registrar Libros](readmeRecursos/LiterAlura-Consola.gif)

## Manejo de Errores

1. **Libro No Encontrado:**
    - Si el usuario busca un libro que no existe en la API, se muestra un mensaje indicando que el libro no fue encontrado.

2. **Duplicación de Libros:**
    - Evité la inserción del mismo libro más de una vez en la base de datos.

## Funcionalidades Adicionales

Además de las funcionalidades básicas, desarrollé una interfaz frontend para hacer la aplicación más amigable y accesible. La interfaz permite a los usuarios interactuar con el catálogo de una manera más visual e intuitiva.

![Interfaz Frontend](readmeRecursos/LiterAlura-FrontEnd.gif)

Para utilizar la interfaz gráfica apóyate de la línea de comandos, dirigiéndote al directorio donde se encuentra la carpeta 'LiterAlura-FrontEnd'. Una vez allí, ejecuta uno de los siguientes comandos, dependiendo de tu sistema operativo.
```bash
npm run dev
# or
yarn dev
# or
pnpm dev
# or
bun dev
```

Para que el comando sea reconocido, es necesario tener instalado Node.js. Además, asegúrate de inicializar la aplicación BackEnd en el puerto 8080, ya que las APIs se obtienen desde `localhost:8080`.

Hecho esto, desde tu navegador accedes al `localhost:3000`.

El puerto puede cambiar si está ocupado por algún otro proceso.

## Recursos Utilizados para el desarrollo BackEnd

- **API de Gutendex:** [gutendex.com](https://gutendex.com)
- **Documentación de Spring Data JPA:** [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- **Documentación de PostgreSQL:** [PostgreSQL](https://www.postgresql.org/docs/)

![Interfaz Frontend](readmeRecursos/badge-literalura.png)

