package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmsService;

import java.util.List;

@Validated
@RestController
@Slf4j
@RequestMapping("/genres")
public class GenresController {

    private final FilmsService filmsService;

    @Autowired
    public GenresController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping
    public List<Genre> findAll() {
        log.info("GET: получение списка всех жанров");
        return filmsService.getListOfAllGenres();
    }

    @GetMapping("/{id}")
    public Genre findById(@PathVariable int id) {
        log.info("GET: получение жанра с id: {}", id);
        return filmsService.findGenreById(id);
    }
}
