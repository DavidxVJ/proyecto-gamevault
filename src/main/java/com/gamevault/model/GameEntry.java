package com.gamevault.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
//uC: Regla de negocio importante que aplicamos a nivel de BD: un usuario no puede tener dos GameEntry para el mismo juego
@Table(name = "game_entries",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "game_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "game"})
@EqualsAndHashCode(of = "id")
public class GameEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //MTM: Muchos GameEntry pueden apuntar al mismo User y al mismo Game.
    @ManyToOne(fetch = FetchType.LAZY)
    //Define explícitamente el nombre de la columna de llave foránea en la tabla game_entries.
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //FT: @ManyToOne es EAGER por defecto en JPA, a diferencia de @ManyToMany y @OneToMany que son LAZY por defecto.
    // Por eso lo forzamos explícitamente a LAZY aquí, para mantener consistencia con el resto del proyecto y evitar
    // cargar User/Game completos cuando no los necesitamos.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    //Le dice a Hibernate: "guarda el enum como texto ('PLAYING') en la base de datos, no como número"
    //Si se omite esta anotación, Hibernate por defecto guarda el índice ordinal del enum (0, 1, 2, 3) en vez del nombre.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    private Integer rating;

    private Integer hoursPlayed;

    @Column(length = 2000)
    private String personalReview;
}