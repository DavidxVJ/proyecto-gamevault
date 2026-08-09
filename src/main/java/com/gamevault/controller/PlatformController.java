// com.gamevault.controller.PlatformController
package com.gamevault.controller;

import com.gamevault.model.Platform;
import com.gamevault.service.PlatformService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platforms")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public List<Platform> getAllPlatforms() {
        return platformService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Platform createPlatform(@RequestBody Platform platform) {
        return platformService.create(platform);
    }
}