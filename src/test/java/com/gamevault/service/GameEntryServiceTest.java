package com.gamevault.service;

import com.gamevault.dto.GameEntryRequest;
import com.gamevault.exception.DuplicateResourceException;
import com.gamevault.exception.ResourceNotFoundException;
import com.gamevault.model.Game;
import com.gamevault.model.GameEntry;
import com.gamevault.model.GameStatus;
import com.gamevault.model.User;
import com.gamevault.repository.GameEntryRepository;
import com.gamevault.repository.GameRepository;
import com.gamevault.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameEntryServiceTest {

    @Mock
    private GameEntryRepository gameEntryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameEntryService gameEntryService;

    private User sampleUser;
    private Game sampleGame;
    private GameEntryRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setUsername("gamer123");

        sampleGame = new Game();
        sampleGame.setId(1L);
        sampleGame.setTitle("Elden Ring");

        sampleRequest = new GameEntryRequest();
        sampleRequest.setGameId(1L);
        sampleRequest.setStatus(GameStatus.PLAYING);
        sampleRequest.setRating(9);
        sampleRequest.setHoursPlayed(45);
    }

    @Test
    void create_deberiaCrearEntry_cuandoNoHayDuplicado() {
        //thenReturn nos permite decir "lo que sea que te pasen como argumento, devuélvelo tal cual"
        when(gameRepository.findById(1L)).thenReturn(Optional.of(sampleGame));
        when(gameEntryRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.empty());
        when(gameEntryRepository.save(any(GameEntry.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GameEntry result = gameEntryService.create(sampleUser, sampleRequest);

        assertThat(result.getUser()).isEqualTo(sampleUser);
        assertThat(result.getGame()).isEqualTo(sampleGame);
        assertThat(result.getStatus()).isEqualTo(GameStatus.PLAYING);
        verify(gameEntryRepository).save(any(GameEntry.class));
    }

    @Test
    void create_deberiaLanzarExcepcion_cuandoJuegoNoExiste() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());
        sampleRequest.setGameId(99L);

        assertThatThrownBy(() -> gameEntryService.create(sampleUser, sampleRequest))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_deberiaLanzarExcepcion_cuandoYaExisteEntry() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(sampleGame));
        when(gameEntryRepository.findByUserIdAndGameId(1L, 1L))
                .thenReturn(Optional.of(new GameEntry()));

        assertThatThrownBy(() -> gameEntryService.create(sampleUser, sampleRequest))
                .isInstanceOf(DuplicateResourceException.class);
    }
}