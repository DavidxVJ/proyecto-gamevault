//Cerebro de la app. Reglas, validaciones, calculos, etc.
package com.gamevault.service;

import com.gamevault.model.Game;
import com.gamevault.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //marca esta clase como un bean gestionado por Spring
public class GameService {

    //una vez asignado en el constructor, esta referencia no puede volver a cambiar
    private final GameRepository gameRepository;

    //Este tipo de constructor se llama inyeccion de dependencias por constructor. Nunca se escribe new GameRepository()
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    //Si no existe, lanza una excepcion
    public Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado con id: " + id));
    }

    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public Game update(Long id, Game gameDetails) {
        Game game = findById(id);
        game.setTitle(gameDetails.getTitle());
        game.setDeveloper(gameDetails.getDeveloper());
        game.setReleaseYear(gameDetails.getReleaseYear());
        game.setDescription(gameDetails.getDescription());
        return gameRepository.save(game);
    }

    public void delete(Long id) {
        gameRepository.delete(findById(id));
    }
}