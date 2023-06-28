package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenresStorage {

    Genre addGenre(Genre genre);

    void addFilmGenre(Film film);

    Optional<Genre> findById(int id);

    List<Genre> getListOfAllGenre();

    void updateGenreForFilm(Film film);

    Set<Genre> findGenresByFilmID(int filmId);
}
