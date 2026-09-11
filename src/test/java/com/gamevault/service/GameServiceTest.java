package com.gamevault.service;

import com.gamevault.exception.ResourceNotFoundException;
import com.gamevault.model.Game;
import com.gamevault.repository.GameRepository;
import com.gamevault.repository.GenreRepository;
import com.gamevault.repository.PlatformRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//esto es lo que hace que las anotaciones @Mock e @InjectMocks funcionen
@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    //crea una versión falsa de GameRepository. No toca ninguna base de datos real
    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlatformRepository platformRepository;

    @Mock
    private GenreRepository genreRepository;

    //le dice a Mockito "crea una instancia real de GameService, pero inyéctale los mocks de arriba en vez de las
    //dependencias reales"
    @InjectMocks
    private GameService gameService;

    private Game sampleGame;

    @BeforeEach
    void setUp() {
        sampleGame = new Game();
        sampleGame.setId(1L);
        sampleGame.setTitle("Elden Ring");
        sampleGame.setDeveloper("FromSoftware");
        sampleGame.setReleaseYear(2022);
    }

    @Test
    void findById_deberiaRetornarJuego_cuandoExiste() {
        //Le decimos: "cuando alguien llame gameRepository.findById(1L), no vayas a ninguna BD,
        //simplemente devuelve este Optional que yo ya preparé"
        when(gameRepository.findById(1L)).thenReturn(Optional.of(sampleGame));

        Game result = gameService.findById(1L);

        assertThat(result.getTitle()).isEqualTo("Elden Ring");
        verify(gameRepository).findById(1L);
    }

    @Test
    void findById_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gameService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void create_deberiaGuardarYRetornarJuego() {
        when(gameRepository.save(sampleGame)).thenReturn(sampleGame);

        Game result = gameService.create(sampleGame);

        assertThat(result).isEqualTo(sampleGame);
        verify(gameRepository).save(sampleGame);
    }

    @Test
    void delete_deberiaEliminarJuego_cuandoExiste(){
        when(gameRepository.findById(1L)).thenReturn(Optional.of(sampleGame));

        gameService.delete(sampleGame.getId());

        verify(gameRepository).delete(sampleGame);
    }
}