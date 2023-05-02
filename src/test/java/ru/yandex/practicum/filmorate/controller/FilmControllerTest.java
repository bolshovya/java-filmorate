package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController filmController;
    private Film film1;
    private Film film2;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController();
        film1 = new Film("Большой куш", "комедия Гая Ричи", "2000-09-01", 104);
        film2 = new Film("Карты, деньги, два ствола", "черная комедия Гая Ричи", "1998-01-01", 106);
    }

    @Test
    void shouldReturnFilm() throws ValidationException {
        assertEquals(film1, filmController.create(film1));
        assertNotNull(filmController.findAll());
    }

    @Test
    void shouldReturnException() throws ValidationException {
        Film film = new Film("Ирония судьбы", "комедия из СССР", "1800-01-01", 0);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals("Release date not earlier than 28 December 1895.", exception.getMessage());
    }

    @Test
    void shouldReturnFilmList() throws ValidationException {
        assertEquals(0, filmController.findAll().size());
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