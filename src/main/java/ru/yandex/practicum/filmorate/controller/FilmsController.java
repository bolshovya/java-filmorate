package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmsService;
import ru.yandex.practicum.filmorate.model.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmsController {

    private final FilmsService filmsService = new FilmsService();

    @GetMapping
    public List<Film> findAll() {
        log.debug("Sum of films: " + filmsService.getSizeStorage());
        return filmsService.getListOfAllFilms();
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable int id) {
        return filmsService.findById(id);
    }

    @GetMapping("/popular")
    public Set<Film> findTopFilm(@RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        return filmsService.findTopFilms(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmsService.addFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmsService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLiker(@PathVariable int id, @PathVariable int userId) {
        return filmsService.addLiker(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public List<Film> removeLiker(@PathVariable int id, @PathVariable int userId) {
        return filmsService.removeLiker(id, userId);
    }

}
