package com.gamevault.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "games")
@EqualsAndHashCode(exclude = "games")

public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Game> games = new HashSet<>();
}