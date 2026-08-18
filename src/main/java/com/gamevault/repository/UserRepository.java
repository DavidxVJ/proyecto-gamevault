package com.gamevault.repository;

import com.gamevault.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Query Derivation: Spring genera el query automáticamente a partir del nombre del método.
    Optional<User> findByUsername(String username);
}