package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.model.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private FilmService filmService = new FilmService();

    @GetMapping
    public List<Film> findAll() {
        log.debug("Sum of films: " + filmService.getSizeStorage());
        return filmService.getListOfAllFilms();
    }

    @GetMapping("/film/{id}")
    public Film findById(@PathVariable int id) {
        return filmService.findById(id);
    }

    @GetMapping("/popular")
    public Set<Film> findTopFilm(@RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        return filmService.findTopFilms(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLiker(@PathVariable int id, @PathVariable int userId) {
        return filmService.addLiker(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLiker(@PathVariable int id, @PathVariable int userId) {
        return filmService.removeLiker(id, userId);
    }

}
