//Antes de los DTOs, tenemos un problema el cual es un error de diseño: estamos exponiendo las entidades JPA directamente como respuesta de la API.
//Esto es una mala practica por varias razones como acomplamiento peligroso, exponer mas de lo necesario, referencias circulares (leer retroalimentacion)

//DTO (Data Trasnfer Objects) es una clase simple que solo representa la forma en que se quiere exponer datos hacia afuera (o recibirlos desde afuera), completamente separada de la entidad JPA.

package com.gamevault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private Long id;
    private String title;
    private String developer;
    private Integer releaseYear;
    private String description;

    //Aqui no hay Set<Platform> ni Set<Genre>, solo Set<String> con los nombres. Cortamos la relación circular de raíz porque el DTO ni siquiera conoce esas entidades, solo extrae el dato que nos interesa mostrar.
    private Set<String> platformNames;
    private Set<String> genreNames;
}
