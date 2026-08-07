//Representacion de las tablas de mi BD.
package com.gamevault.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}