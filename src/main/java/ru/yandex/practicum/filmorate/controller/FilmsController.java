package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final FilmsService filmsService;

    @Autowired
    public FilmsController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }


    @GetMapping
    public List<Film> findAll() {
        log.info("GET-запрос списка всех фильмов: {}", filmsService.getSizeStorage());
        return filmsService.getListOfAllFilms();
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable int id) {
        log.info("GET-запрос фильма по ID: {}", filmsService.findById(id));
        return filmsService.findById(id);
    }

    @GetMapping("/popular")
    public Set<Film> findTopFilm(@RequestParam(value = "count", defaultValue = "10") int count) {
        log.info("GET-запрос ТОП {} фильмов", count);
        return filmsService.findTopFilms(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("POST-запрос. Добавление фильма: {}", film);
        return filmsService.addFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("PUT-запрос. Обновление фильма: {}", film);
        return filmsService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLiker(@PathVariable int id, @PathVariable int userId) {
        log.info("PUT-запрос. Добавление лайка фильму c ID {} от пользователя с ID {}", id, userId);
        return filmsService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLiker(@PathVariable int id, @PathVariable int userId) {
        log.info("DELETE-запрос. Удаление лайка у фильма с ID {} от пользователя с ID {}", id, userId);
        filmsService.removeLike(id, userId);
    }

}
