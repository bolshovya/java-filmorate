package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmStorage filmStorage;

    @Autowired
    public FilmService() {
        this.filmStorage = new InMemoryFilmStorage();
    }

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

    public Film addLiker(int filmId, int userId) { // добавление лайка
        Film searchedFilm = filmStorage.findById(filmId);
        searchedFilm.addLiker(userId);
        return filmStorage.updateFilm(searchedFilm);
    }

    public Film removeLiker(int filmId, int userId) {  // удаление лайка
        Film searchedFilm = filmStorage.findById(filmId);
        searchedFilm.removeLiker(userId);
        return filmStorage.updateFilm(searchedFilm);
    }

    public Set<Film> findTopFilms(int count) {   // вывод 10 наиболее популярных фильмов по количеству лайков
        return filmStorage.getListOfAllFilms().stream()
                .sorted(Comparator.comparing(Film::getLikeCount).reversed())
                .limit(count)
                .collect(Collectors.toSet());
    }
}
