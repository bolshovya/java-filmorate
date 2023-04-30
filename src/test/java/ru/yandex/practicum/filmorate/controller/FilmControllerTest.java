package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {



    @BeforeEach
    void beforeEach() {
        FilmController filmController = new FilmController();
        Film film1 = new Film(1, "Большой куш", );
    }

}