package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmStorage filmStorage = new InMemoryFilmStorage();

    public List<Film> getListOfAllFilms() {
        return filmStorage.getListOfAllFilms();
    }

    public int getSizeStorage() {
        return filmStorage.getSizeStorage();
    }

    public Film addFilm(Film addedFilm) {
        return filmStorage.addFilm(addedFilm);
    }

    public Film updateFilm(Film updatedFilm) {
        return filmStorage.updateFilm(updatedFilm);
    }

    public Film findById(int id) {
        return filmStorage.findById(id);
    }

    /*
    добавление лайка
     */
    public void addLiker(int filmId, int userId) {
        Film searchedFilm = filmStorage.findById(filmId);
        searchedFilm.addLiker(userId);
    }

    /*
    удаление лайка
     */
    public void removeLiker(int filmId, int userId) {
        Film searchedFilm = filmStorage.findById(filmId);
        searchedFilm.removeLiker(userId);
    }

    /*
    вывод 10 наиболее популярных фильмов по количеству лайков.
     */
    public Set<Film> returnTop10Films() {
        return filmStorage.getListOfAllFilms().stream()
                .sorted(Comparator.comparing(Film::getLikeCount).reversed())
                .limit(10)
                .collect(Collectors.toSet());
    }
}
