//Representacion de las tablas de mi BD.
package com.gamevault.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity //le dice a SpringBoot/Hibernate que esta clase representa una tabla en la BD
@Table(name = "games") //opcional. Por defecto Hibernate ocupa el nombre de la clase como nombre de la tabla

//Lombok - libreria que elimina codigo repetitivo
@Data //genera en tiempo de compilacion los getters, setters, equals, hashCode y toString
@NoArgsConstructor //genera constructor no vacio
@AllArgsConstructor //genera constructor con todos los parametros
public class Game {

    @Id //lave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id generado automaticamente
    private Long id;

    @Column(nullable = false) //a nivel BD, esta columna no puede ser nula
    private String title;

    private String developer;

    private Integer releaseYear;

    @Column(length = 1000) //por defecto es VARCHAR(25). Con esto decimos que queremos mas espacio
    private String description;

    //En una relacion ManyToMany alguien tiene que ser el dueño. Normalmente el dueño es la entidad "principal" desde la que sueles navegar la relación en tu lógica de negocio.
    //Game es el dueño: por eso tiene @JoinTable, que le dice explícitamente a Hibernate "crea una tabla llamada game_platforms con las columnas game_id y platform_id".
    //Platform y Genre son el "lado inverso": por eso usan mappedBy, que significa literalmente "esta relación ya está definida del otro lado, en el campo platforms de la clase Game
    @ManyToMany
    @JoinTable(
            name = "game_platforms",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private Set<Platform> platforms = new HashSet<>(); //Usamos Set<Platform> en lugar de List<Platform> porque un juego no debería tener la misma plataforma duplicada dos veces.

    @ManyToMany
    @JoinTable(
            name = "game_genres",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();
}