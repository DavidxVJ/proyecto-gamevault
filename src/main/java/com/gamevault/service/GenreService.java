// com.gamevault.service.GenreService
package com.gamevault.service;

import com.gamevault.model.Genre;
import com.gamevault.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre create(Genre genre) {
        return genreRepository.save(genre);
    }
}