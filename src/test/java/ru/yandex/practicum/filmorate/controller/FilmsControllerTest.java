package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmsControllerTest {
    private FilmsController filmsController;
    private Film film1;
    private Film film2;

    @BeforeEach
    void beforeEach() {
        filmsController = new FilmsController();
        film1 = new Film("Большой куш", "комедия Гая Ричи", "2000-09-01", 104);
        film2 = new Film("Карты, деньги, два ствола", "черная комедия Гая Ричи", "1998-01-01", 106);
    }

    @Test
    void shouldReturnFilm() {
        assertEquals(film1, filmsController.create(film1));
        assertNotNull(filmsController.findAll());
    }

    @Test
    void shouldReturnException() { // дата релиза — не раньше 28 декабря 1895 года;
        Film film = new Film("Ирония судьбы", "комедия из СССР", "1800-01-01", 184);
        Exception exception = assertThrows(ValidationException.class, () -> filmsController.create(film));
        assertEquals("Release date not earlier than 28 December 1895.", exception.getMessage());
    }

    @Test
    void shouldReturnExceptionNameTest() {  // название не может быть пустым;
        Film film = new Film("", "комедия из СССР", "1975-01-01", 184);
        Exception exception = assertThrows(ValidationException.class, () -> filmsController.create(film));
        assertEquals("Added film doesn't contains name.", exception.getMessage());
    }

    @Test
    void shouldReturnExceptionDescriptionMaxCharsTest() {  // максимальная длина описания — 200 символов;
        Film film = new Film("Ирония судьбы", "Советский двухсерийный телевизионный фильм режиссёра " +
                "Эльдара Рязанова, созданный в 1975 году и впервые показанный в Советском Союзе 1 января 1976 года " +
                "по «Первой программе ЦТ». Аудитория первого показа оценивается в 100 миллионов зрителей.", "1975-01-01", 100);
        Exception exception = assertThrows(ValidationException.class, () -> filmsController.create(film));
        assertEquals("The description of the added film contains more than 200 chars.", exception.getMessage());
    }

    @Test
    void shouldReturnFilmList() {
        assertEquals(0, filmsController.findAll().size());
        filmsController.create(film1);
        filmsController.create(film2);
        assertEquals(List.of(film1, film2), filmsController.findAll());
    }

    @Test
    void shouldUpdateFilm() {
        filmsController.create(film1);
        film1.setDescription("Гая Ричи комедия");
        filmsController.update(film1);
        assertEquals("Гая Ричи комедия", filmsController.findAll().get(0).getDescription());
    }



}