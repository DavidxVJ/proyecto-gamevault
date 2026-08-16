package com.gamevault.controller;

import com.gamevault.dto.GameEntryMapper;
import com.gamevault.dto.GameEntryRequest;
import com.gamevault.dto.GameEntryResponse;
import com.gamevault.service.GameEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game-entries")
public class GameEntryController {

    private final GameEntryService gameEntryService;

    public GameEntryController(GameEntryService gameEntryService) {
        this.gameEntryService = gameEntryService;
    }

    @GetMapping("/user/{userId}")
    public List<GameEntryResponse> getByUser(@PathVariable Long userId) {
        return gameEntryService.findByUser(userId).stream()
                .map(GameEntryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameEntryResponse create(@RequestBody GameEntryRequest request) {
        return GameEntryMapper.toResponse(gameEntryService.create(request));
    }
}