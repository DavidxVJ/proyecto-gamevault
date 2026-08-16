package com.gamevault.service;

import com.gamevault.dto.GameEntryRequest;
import com.gamevault.exception.DuplicateResourceException;
import com.gamevault.exception.ResourceNotFoundException;
import com.gamevault.model.Game;
import com.gamevault.model.GameEntry;
import com.gamevault.model.User;
import com.gamevault.repository.GameEntryRepository;
import com.gamevault.repository.GameRepository;
import com.gamevault.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameEntryService {

    private final GameEntryRepository gameEntryRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public GameEntryService(GameEntryRepository gameEntryRepository,
                            UserRepository userRepository,
                            GameRepository gameRepository) {
        this.gameEntryRepository = gameEntryRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public List<GameEntry> findByUser(Long userId) {
        return gameEntryRepository.findByUserId(userId);
    }

    public GameEntry create(GameEntryRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Game game = gameRepository.findById(request.getGameId())
                .orElseThrow(() -> new ResourceNotFoundException("Juego no encontrado"));

        //findByUserIdAndGameId(...) devuelve un Optional<GameEntry>. ifPresent(...) recibe una función que solo se
        //ejecuta si el Optional tiene un valor (es decir, si ya existe un registro previo). Si está vacío, simplemente
        //no hace nada y el código sigue de largo.
        gameEntryRepository.findByUserIdAndGameId(request.getUserId(), request.getGameId())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Este usuario ya tiene un registro para este juego");
                });

        GameEntry entry = new GameEntry();
        entry.setUser(user);
        entry.setGame(game);
        entry.setStatus(request.getStatus());
        entry.setRating(request.getRating());
        entry.setHoursPlayed(request.getHoursPlayed());
        entry.setPersonalReview(request.getPersonalReview());

        return gameEntryRepository.save(entry);
    }
}