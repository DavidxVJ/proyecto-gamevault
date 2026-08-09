// com.gamevault.service.PlatformService
package com.gamevault.service;

import com.gamevault.model.Platform;
import com.gamevault.repository.PlatformRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;

    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public List<Platform> findAll() {
        return platformRepository.findAll();
    }

    public Platform create(Platform platform) {
        return platformRepository.save(platform);
    }
}