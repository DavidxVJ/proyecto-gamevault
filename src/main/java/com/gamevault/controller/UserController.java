package com.gamevault.controller;

import com.gamevault.dto.UserResponse;
import com.gamevault.model.User;
import com.gamevault.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail()))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody User user) {
        User saved = userService.create(user);
        return new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail());
    }
}