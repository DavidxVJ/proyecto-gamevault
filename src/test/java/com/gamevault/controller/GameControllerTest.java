package com.gamevault.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamevault.model.Game;
import com.gamevault.model.User;
import com.gamevault.repository.GameRepository;
import com.gamevault.repository.UserRepository;
import com.gamevault.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Esta anotación levanta el contexto completo de Spring Boot. Todos los beans reales (GameService,
//GameRepository conectado a H2, SecurityConfig, todo). Es mucho más "pesado" y lento que un unit test,
//pero prueba la integración real entre piezas.
@SpringBootTest
//Nos da acceso a MockMvc, una herramienta que simula peticiones HTTP sin necesitar levantar un servidor
//Tomcat real ni usar Postman
@AutoConfigureMockMvc
class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private String token;

    @BeforeEach
    void setUp() {
        gameRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@ejemplo.com");
        user.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user);

        token = jwtService.generateToken("testUser");
    }

    @Test
    void getAllGames_deberiaRetornar200_conTokenValido() throws Exception {
        mockMvc.perform(get("/api/games")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void getAllGames_deberiaRetornar403_sinToken() throws Exception {
        mockMvc.perform(get("/api/games"))
                .andExpect(status().isForbidden());
    }

    @Test
    void createGame_deberiaCrearJuego_conDatosValidos() throws Exception {
        Game newGame = new Game();
        newGame.setTitle("Hades");
        newGame.setDeveloper("Supergiant Games");
        newGame.setReleaseYear(2020);

        mockMvc.perform(post("/api/games")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newGame)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Hades"))
                .andExpect(jsonPath("$.id").exists());
    }
}