package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmsService;

import java.util.List;

@Validated
@RestController
@Slf4j
@RequestMapping("/mpa")
public class MpaController {

    private final FilmsService filmsService;

    @Autowired
    public MpaController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping
    public List<Mpa> findAll() {
        log.info("GET: получение списка всех рейтингов");
        return filmsService.getListOfAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa findById(@PathVariable int id) {
        log.info("GET: получение рейтинга с id: {}", id);
        return filmsService.findMpaById(id);
    }


}
