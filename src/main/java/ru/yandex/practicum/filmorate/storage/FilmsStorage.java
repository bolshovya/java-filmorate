package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmsStorage {

    Film addFilm(Film addedFilm);

    Optional<Film> findById(int id);

    List<Film> getListOfAllFilms();

    Film updateFilm(Film updatedFilm);

    Film removeFilmFromStorage(Film removedFilm);

    int getSizeStorage();

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);
}
