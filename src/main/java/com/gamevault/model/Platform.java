package com.gamevault.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "platforms")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "games")
@EqualsAndHashCode(exclude = "games")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    //Muchas plataformas tienen muchos juegos
    @ManyToMany(mappedBy = "platforms")
    private Set<Game> games = new HashSet<>();
}