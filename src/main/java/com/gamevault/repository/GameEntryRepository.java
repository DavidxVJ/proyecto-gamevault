package com.gamevault.repository;

import com.gamevault.model.GameEntry;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameEntryRepository extends JpaRepository<GameEntry, Long> {

    //Los dos siguientes metodos se llaman query derivation (derivación de consultas): Spring Data JPA lee el nombre
    // del método y genera la consulta SQL automáticamente basándose en esa convención de nombres.

    @EntityGraph(attributePaths = {"user", "game"})
    List<GameEntry> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "game"})
    Optional<GameEntry> findByUserIdAndGameId(Long userId, Long gameId);
}