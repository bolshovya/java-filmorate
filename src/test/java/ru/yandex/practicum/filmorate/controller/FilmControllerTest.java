package ru.yandex.practicum.filmorate.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.manager.FilmManager;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController filmController;
    private FilmManager filmManager;
    private Film film1;
    private Film film2;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController();
        filmManager = new FilmManager();
        film1 = new Film("Большой куш", "комедия Гая Ричи", LocalDate.of(2000, 9, 1), Duration.ofMinutes(104));
        film2 = new Film("Карты, деньги, два ствола", "черная комедия Гая Ричи", LocalDate.of(1998,1,1), Duration.ofMinutes(106));
    }

    @Test
    void shouldReturnFilm() throws ValidationException {
        assertEquals(film1, filmController.create(film1));
        assertNotNull(filmController.findAll());
    }

    @Test
    void shouldReturnException() throws ValidationException {
        Film film = new Film("Ирония судьбы", "комедия из СССР", LocalDate.of(1800,1,1), Duration.ZERO);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Release date not earlier than 28 December 1895.", exception.getMessage());
    }

    @Test
    void shouldReturnFilmList() throws ValidationException {
        filmController.create(film1);
        filmController.create(film2);
        assertEquals(List.of(film1, film2), filmController.findAll());
    }

    @Test
    void shouldUpdateFilm() throws ValidationException, ManagerException {
        filmController.create(film1);
        film1.setDescription("Гая Ричи комедия");
        filmController.update(film1);
        assertEquals("Гая Ричи комедия", filmController.findAll().get(0).getDescription());
    }

}