package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.manager.FilmManager;
import ru.yandex.practicum.filmorate.model.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private FilmManager filmManager = new FilmManager();

    @GetMapping
    public List<Film> findAll() {
        log.debug("Sum of films: " + filmManager.getSizeStorage());
        return filmManager.getListOfAllFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmManager.addFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmManager.updateFilm(film);
    }


}
