package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.FilmsService;

import java.util.List;

@Validated
@RestController
@Slf4j
@RequestMapping("/mpa")
public class RatingsController {

    private final FilmsService filmsService;

    @Autowired
    public RatingsController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping
    public List<Rating> findAll() {
        log.info("GET-запрос списка всех рейтингов.");
        return filmsService.getListOfAllRatings();
    }

    @GetMapping("/{id}")
    public Rating findById(@PathVariable int id) {
        log.info("GET-запрос рейтинга по ID: {}", id);
        return filmsService.findRatingById(id);
    }


}
