//Puerta de entrada para las peticiones HTTP (GET, POST, PUT, DELETE).
package com.gamevault.controller;

import com.gamevault.dto.GameMapper;
import com.gamevault.dto.GameResponse;
import com.gamevault.model.Game;
import com.gamevault.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController //combina @Controller + @ResponseBody. Le dice a Spring "esta clase maneja peticiones HTTP y devuelve directamente datos (JSON), no vistas HTML"
@RequestMapping("/api/games") //prefijo de ruta para todos los endpoints de esta clase
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameResponse> getAllGames() {
        return gameService.findAll().stream()
                .map(GameMapper::toResponse) //GameMapper::toResponse es una referencia a método equivalente a escribir .map(game -> GameMapper.toResponse(game)), pero más limpio. Es azúcar sintáctica.
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GameResponse getGameById(@PathVariable Long id) {
        return GameMapper.toResponse(gameService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponse createGame(@RequestBody Game game) {
        return GameMapper.toResponse(gameService.create(game));
    }

    @PutMapping("/{id}")
    public GameResponse updateGame(@PathVariable Long id, @RequestBody Game game) {
        return GameMapper.toResponse(gameService.update(id, game));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //por defecto Spring devuelve 200 OK en todo. Aquí somos explícitos: crear devuelve 201 (Created), eliminar devuelve 204 (No Content)
    public void deleteGame(@PathVariable Long id) {
        gameService.delete(id);
    }

    @PutMapping("/{id}/platforms")
    public GameResponse assignPlatforms(@PathVariable Long id, @RequestBody Set<Long> platformIds) {
        return GameMapper.toResponse(gameService.assignPlatforms(id, platformIds));
    }

    @PutMapping("/{id}/genres")
    public GameResponse assignGenres(@PathVariable Long id, @RequestBody Set<Long> genreIds) {
        return GameMapper.toResponse(gameService.assignGenres(id, genreIds));
    }
}