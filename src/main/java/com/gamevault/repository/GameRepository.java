package com.gamevault.repository;

import com.gamevault.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //opcional pero buena practica
//SpringDataJPA genera automaticamente una clase que implementa GameRepository con todos los CRUD
public interface GameRepository extends JpaRepository<Game, Long> { //JpaRepository - Interfaz generica. Game es el tipo de entidad que maneja, Long es el tipo del campo @Id.
    
}