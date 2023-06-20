package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmsStorage;
import ru.yandex.practicum.filmorate.storage.GenresStorage;
import ru.yandex.practicum.filmorate.storage.RatingsStorage;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmsService {

    private FilmsStorage filmsStorage;
    private GenresStorage genresStorage;
    private RatingsStorage ratingsStorage;

    @Autowired
    public FilmsService(@Qualifier("filmsDbStorage") FilmsStorage filmsStorage, GenresStorage genresStorage,
                        RatingsStorage ratingsStorage) {
        this.filmsStorage = filmsStorage;
        this.genresStorage = genresStorage;
        this.ratingsStorage = ratingsStorage;
    }

    public Film addFilm(Film addedFilm) {
        return filmsStorage.addFilm(addedFilm);
    }

    public Film findById(int id) {
        return filmsStorage.findById(id);
    }

    public List<Film> getListOfAllFilms() {
        return filmsStorage.getListOfAllFilms();
    }

    public Film updateFilm(Film updatedFilm) {
        return filmsStorage.updateFilm(updatedFilm);
    }

    public int getSizeStorage() {
        return filmsStorage.getSizeStorage();
    }

    public Genre findGenreById(int id) {
        return genresStorage.findById(id);
    }

    public List<Genre> getListOfAllGenres() {
        return genresStorage.getListOfAllGenre();
    }

    public Rating findRatingById(int id) {
        return ratingsStorage.findById(id);
    }

    public List<Rating> getListOfAllRatings() {
        return ratingsStorage.getListOfAllRatings();
    }

    public Film addLike(int filmId, int userId) { // добавление лайка
        Film searchedFilm = filmsStorage.findById(filmId);
        searchedFilm.addLike(userId);
        return filmsStorage.updateFilm(searchedFilm);
    }

    public void removeLike(int filmId, int userId) {  // удаление лайка
        if (filmId < 0 || userId < 0) {
            throw new ManagerException("Параметр id имеет отрицательное значение.");
        }
        Film searchedFilm = filmsStorage.findById(filmId);
        searchedFilm.removeLike(userId);
        filmsStorage.updateFilm(searchedFilm);
    }

    public Set<Film> findTopFilms(int count) {   // вывод 10 наиболее популярных фильмов по количеству лайков
        return filmsStorage.getListOfAllFilms().stream()
                .sorted(Comparator.comparing(Film::getLikeCount).reversed())
                .limit(count)
                .collect(Collectors.toSet());
    }
}
