// com.gamevault.repository.PlatformRepository
package com.gamevault.repository;

import com.gamevault.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
}