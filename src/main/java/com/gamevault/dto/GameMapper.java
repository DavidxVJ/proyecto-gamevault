package com.gamevault.dto;

import com.gamevault.model.Game;
import com.gamevault.model.Genre;
import com.gamevault.model.Platform;

import java.util.Set;

import java.util.stream.Collectors;

public class GameMapper {

    //toResponse sera static porque GameMapper no tiene estado, no guarda nada, no depende de ninguna instancia. Es una función pura: le das un Game, te devuelve un GameResponse, siempre de la misma forma.
    public static GameResponse toResponse(Game game) {
        Set<String> platformNames = game.getPlatforms().stream()
                .map(Platform::getName)
                .collect(Collectors.toSet());

        Set<String> genreNames = game.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toSet());

        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getDeveloper(),
                game.getReleaseYear(),
                game.getDescription(),
                platformNames,
                genreNames
        );
    }
}