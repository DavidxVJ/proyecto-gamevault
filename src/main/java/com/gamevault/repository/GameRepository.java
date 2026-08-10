//Acceso a la BD. CRUD.
package com.gamevault.repository;

import com.gamevault.model.Game;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //opcional pero buena practica
//SpringDataJPA genera automaticamente una clase que implementa GameRepository con todos los CRUD
public interface GameRepository extends JpaRepository<Game, Long> { //JpaRepository - Interfaz generica. Game es el tipo de entidad que maneja, Long es el tipo del campo @Id.

    //EntityGraph le dice a Spring Data JPA: "cuando ejecutes findAll(), trae también platforms y genres en la(s) misma(s) consulta(s), no esperes a que alguien los toque después"
    @EntityGraph(attributePaths = {"platforms", "genres"})
    List<Game> findAll();
}