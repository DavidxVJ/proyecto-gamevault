package com.gamevault.controller;

import com.gamevault.dto.GameEntryMapper;
import com.gamevault.dto.GameEntryRequest;
import com.gamevault.dto.GameEntryResponse;
import com.gamevault.service.GameEntryService;
import jakarta.validation.Valid;
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
    //Valid: Cuando Spring recibe la petición, antes de ejecutar el método create() del controller, revisa las
    // anotaciones de validación en GameEntryRequest (@NotNull, @Min, @Max). Si algo no cumple, Spring lanza
    // automáticamente una MethodArgumentNotValidException sin que el código de negocio siquiera se ejecute — el
    // GameEntryService.create() nunca llega a correr si los datos son inválidos.
    public GameEntryResponse create(@Valid @RequestBody GameEntryRequest request) {
        return GameEntryMapper.toResponse(gameEntryService.create(request));
    }
}