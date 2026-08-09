// com.gamevault.repository.GenreRepository
package com.gamevault.repository;

import com.gamevault.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}