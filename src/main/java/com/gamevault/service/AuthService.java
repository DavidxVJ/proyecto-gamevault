package com.gamevault.service;

import com.gamevault.dto.AuthResponse;
import com.gamevault.dto.LoginRequest;
import com.gamevault.dto.RegisterRequest;
import com.gamevault.exception.DuplicateResourceException;
import com.gamevault.model.User;
import com.gamevault.repository.UserRepository;
import com.gamevault.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException("El username ya está en uso");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtService.generateToken(request.getUsername());
        return new AuthResponse(token, request.getUsername());
        /*
        IMPORTANTE
        1. Le pasamos un UsernamePasswordAuthenticationToken con las credenciales sin verificar (username + password
        en texto plano, tal como llegaron del request).
        2. El AuthenticationManager delega en el AuthenticationProvider que configuramos (DaoAuthenticationProvider).
        3. Ese provider usa el CustomUserDetailsService que ya construimos para buscar al usuario por username.
        4. Usa el PasswordEncoder (BCrypt) para comparar la contraseña que llegó contra el hash guardado en la base
        de datos.
        5. Si no coinciden, lanza automáticamente una excepción (BadCredentialsException) — nunca llegamos a la
        línea de generateToken.
        6. Si coinciden, el método simplemente termina sin lanzar nada, y seguimos adelante generando el token.
        */
    }
}