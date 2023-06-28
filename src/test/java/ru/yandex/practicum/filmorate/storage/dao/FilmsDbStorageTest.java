package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(scripts = {"/schema.sql", "/filmsDbStorageTest.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FilmsDbStorageTest {

    private final FilmsDbStorage filmsDbStorage;


    @Test
    void addFilm() {
        Mpa mpa = new Mpa();
        mpa.setId(1);
        Film film = new Film("Кровавый четверг", "Криминальный фильма",
                LocalDate.of(1998,1,1),83, mpa);
        filmsDbStorage.addFilm(film);
        assertThat(filmsDbStorage.findById(3)).get().hasFieldOrPropertyWithValue("name", "Кровавый четверг");
    }

    @Test
    void findById() {
        assertThat(filmsDbStorage.findById(1).get())
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Большой куш")
                .hasFieldOrPropertyWithValue("description", "Отличный фильм, нравится детям")
                .hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(2000,9,1))
                .hasFieldOrPropertyWithValue("duration", 104);
    }

    @Test
    void getListOfAllFilms() {
        assertEquals(2, filmsDbStorage.getListOfAllFilms().size());
    }

    @Test
    void updateFilm() {
        Film film = filmsDbStorage.findById(1).get();
        film.setDescription("Новое описание");
        filmsDbStorage.updateFilm(film);
        assertThat(filmsDbStorage.findById(1).get())
                .hasFieldOrPropertyWithValue("description", "Новое описание");
    }

    @Test
    void removeFilmFromStorage() {
        Film film = filmsDbStorage.findById(2).get();
        filmsDbStorage.removeFilmFromStorage(film);
        assertEquals(1, filmsDbStorage.getListOfAllFilms().size());
    }
}