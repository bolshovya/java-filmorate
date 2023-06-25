package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.*;


import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmsService {

    private FilmsStorage filmsStorage;
    private GenresStorage genresStorage;
    private MpaStorage mpaStorage;

    @Autowired
    public FilmsService(@Qualifier("filmsDbStorage") FilmsStorage filmsStorage, GenresStorage genresStorage,
                        MpaStorage mpaStorage) {
        this.filmsStorage = filmsStorage;
        this.genresStorage = genresStorage;
        this.mpaStorage = mpaStorage;
    }

    public Film addFilm(Film addedFilm) {
        log.info("FilmsService: сохраненеи фильма: {}", addedFilm);
        Film filmToReturn = filmsStorage.addFilm(addedFilm);
        return filmToReturn;
    }

    public Film findById(int id) {
        log.info("FilmsService: получение фильма с id: {}", id);
        return filmsStorage.findById(id).orElseThrow(() -> new FilmNotFoundException("Фильм с id: " + id + " не найден"));
    }

    public List<Film> getListOfAllFilms() {
        log.info("FilmsService: получение списка всех фильмов");
        return filmsStorage.getListOfAllFilms();
    }

    public Film updateFilm(Film updatedFilm) {
        log.info("FilmsService: обновление фильма: {}", updatedFilm);
        return filmsStorage.updateFilm(updatedFilm);
    }

    public int getSizeStorage() {
        log.info("FilmsService: получение размера хранилища с фильмами");
        return filmsStorage.getSizeStorage();
    }

    public Genre findGenreById(int id) {
        log.info("FilmsService: получение жанра с id: {}", id);
        return genresStorage.findById(id).orElseThrow(() -> new GenreNotFoundException("Жанр с id: " + id + " не найден"));
    }

    public List<Genre> getListOfAllGenres() {
        log.info("FilmsService: получение списка всех жанров");
        return genresStorage.getListOfAllGenre();
    }

    public Mpa findMpaById(int id) {
        log.info("FilmService: получение рейтинга  по id: {}", id);
        return mpaStorage.findById(id).orElseThrow(() -> new MpaNotFoundException("Рейтинг с id: " + id + " не найден"));
    }

    public List<Mpa> getListOfAllMpa() {
        log.info("FilmService: получение списка всех рейтингов");
        return mpaStorage.getAll();
    }

    public Film addLike(int filmId, int userId) { // добавление лайка
        log.info("FilmService: добавление лайка фильму с id {} от пользователя с id {}", filmId, userId);
        Film searchedFilm = filmsStorage.findById(filmId).get();
        searchedFilm.addLike(userId);
        return filmsStorage.updateFilm(searchedFilm);
    }

    public void removeLike(int filmId, int userId) {  // удаление лайка
        log.info("FilmService: удаление лайка у фильма с id {} от пользователя с id {}", filmId, userId);
        Film searchedFilm = filmsStorage.findById(filmId).get();
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
