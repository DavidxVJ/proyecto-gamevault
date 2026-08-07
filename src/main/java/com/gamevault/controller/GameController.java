package com.gamevault.controller;

import com.gamevault.model.Game;
import com.gamevault.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //combina @Controller + @ResponseBody. Le dice a Spring "esta clase maneja peticiones HTTP y devuelve directamente datos (JSON), no vistas HTML"
@RequestMapping("/api/games") //prefijo de ruta para todos los endpoints de esta clase
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.findAll();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id) { //PV extrae el valor de la URL (ej. el 5 en /api/games/5)
        return gameService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) { //RB convierte el JSON que llega en el cuerpo de la petición en un objeto Game automáticamente
        return gameService.create(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameService.update(id, game);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //por defecto Spring devuelve 200 OK en todo. Aquí somos explícitos: crear devuelve 201 (Created), eliminar devuelve 204 (No Content)
    public void deleteGame(@PathVariable Long id) {
        gameService.delete(id);
    }
}