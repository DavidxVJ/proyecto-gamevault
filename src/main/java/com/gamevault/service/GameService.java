//Cerebro de la app. Reglas, validaciones, calculos, etc.
package com.gamevault.service;

import com.gamevault.model.Game;
import com.gamevault.model.Genre;
import com.gamevault.model.Platform;
import com.gamevault.repository.GameRepository;
import com.gamevault.repository.GenreRepository;
import com.gamevault.repository.PlatformRepository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service //marca esta clase como un bean gestionado por Spring
public class GameService {

    //una vez asignado en el constructor, esta referencia no puede volver a cambiar
    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;

    public GameService(GameRepository gameRepository, PlatformRepository platformRepository, GenreRepository genreRepository) {
        this.gameRepository = gameRepository;
        this.platformRepository = platformRepository;
        this.genreRepository = genreRepository;
    }

    /*
    Este tipo de constructor se llama inyeccion de dependencias por constructor. Nunca se escribe new GameRepository()
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    */

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

    public Game assignPlatforms(Long gameId, Set<Long> platformIds) {
        Game game = findById(gameId);
        Set<Platform> platforms = new HashSet<>(platformRepository.findAllById(platformIds));
        game.setPlatforms(platforms);
        return gameRepository.save(game);
    }

    public Game assignGenres(Long gameId, Set<Long> genreIds) {
        Game game = findById(gameId);
        //findAllById es un metodo de JpaRepository. Recibe una colección de IDs y te devuelve todas las entidades que coincidan
        Set<Genre> genres = new HashSet<>(genreRepository.findAllById(genreIds));
        game.setGenres(genres);
        return gameRepository.save(game);
    }
}