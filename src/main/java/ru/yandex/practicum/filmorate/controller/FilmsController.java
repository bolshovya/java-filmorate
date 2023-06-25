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
        log.info("GET: получение списка всех фильмов");
        return filmsService.getListOfAllFilms();
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable int id) {
        log.info("GET: получение фильма с id: {}", id);
        return filmsService.findById(id);
    }

    @GetMapping("/popular")
    public Set<Film> findTopFilm(@RequestParam(value = "count", defaultValue = "10") int count) {
        log.info("GET-запрос ТОП {} фильмов", count);
        return filmsService.findTopFilms(count);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("POST: сохранение фильма: {}", film);
        return filmsService.addFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("PUT: обновление фильма: {}", film);
        return filmsService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLiker(@PathVariable int id, @PathVariable int userId) {
        log.info("PUT: добавление лайка фильму c id {} от пользователя с id {}", id, userId);
        return filmsService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLiker(@PathVariable int id, @PathVariable int userId) {
        log.info("DELETE: удаление лайка у фильма с id {} от пользователя с id {}", id, userId);
        filmsService.removeLike(id, userId);
    }

}
