// com.gamevault.controller.PlatformController
package com.gamevault.controller;

import com.gamevault.dto.PlatformResponse;
import com.gamevault.model.Platform;
import com.gamevault.service.PlatformService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/platforms")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public List<PlatformResponse> getAllPlatforms() {
        return platformService.findAll().stream()
                .map(p -> new PlatformResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlatformResponse createPlatform(@RequestBody Platform platform) {
        Platform saved = platformService.create(platform);
        return new PlatformResponse(saved.getId(), saved.getName());
    }
}