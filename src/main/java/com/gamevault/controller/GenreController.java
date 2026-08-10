// com.gamevault.controller.GenreController
package com.gamevault.controller;

import com.gamevault.dto.GenreResponse;
import com.gamevault.model.Genre;
import com.gamevault.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreResponse> getAllGenres() {
        return genreService.findAll().stream()
                .map(p -> new GenreResponse(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreResponse createGenre(@RequestBody Genre genre) {
        Genre saved = genreService.create(genre);
        return new GenreResponse(saved.getId(), saved.getName());
    }
}