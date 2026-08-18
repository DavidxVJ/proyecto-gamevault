package com.gamevault.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password") //Esto es una medida de seguridad. Si algun dia se hace un Sout(user) para depurar,
//no vamos a querer que la contraseña (aunque esté encriptada) aparezca en los logs.
//usamos of en vez de exclude (es lo opuesto). Decimos "usa solo el campo id para calcular igualdad", en vez de excluir campos problemáticos.
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}