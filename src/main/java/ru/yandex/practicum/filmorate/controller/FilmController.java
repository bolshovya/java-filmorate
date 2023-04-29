package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {
        log.debug("Sum of films: " + films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        try {
            if (film.getName().isBlank() || film.getName()==null) {
                throw new ValidationException("Added film doesn't contains name.");
            }
            if (film.getDescription().toCharArray().length > 200) {
                throw new ValidationException("The description of the added film contains more than 200 chars.");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
                throw new ValidationException("Release date not earlier than 28 December 1895.");
            }
            if (film.getDuration().isNegative() || film.getDuration().isZero()) {
                throw new ValidationException("Duration can't be negative or zero.");
            }
            films.put(film.getId(), film);
        } catch (ValidationException e) {
            e.getMessage();
        }
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        try {
            if (film.getName().isBlank() || film.getName()==null) {
                throw new ValidationException("Added film doesn't contains name.");
            }
            if (film.getDescription().toCharArray().length > 200) {
                throw new ValidationException("The description of the added film contains more than 200 chars.");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
                throw new ValidationException("Release date not earlier than 28 December 1895.");
            }
            if (film.getDuration().isNegative() || film.getDuration().isZero()) {
                throw new ValidationException("Duration can't be negative or zero.");
            }
            if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
            }
            if (!films.containsKey(film.getId())) {
                throw new ValidationException("Added film doesn't contain in the list.");
            }
        } catch (ValidationException e) {
            e.getMessage();
        }
        return film;
    }


}
