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
    private UsersStorage usersStorage;
    private LikeStorage likeStorage;


    @Autowired
    public FilmsService(@Qualifier("filmsDbStorage") FilmsStorage filmsStorage, GenresStorage genresStorage,
                        MpaStorage mpaStorage, @Qualifier("usersDbStorage") UsersStorage usersStorage, LikeStorage likeStorage) {
        this.filmsStorage = filmsStorage;
        this.genresStorage = genresStorage;
        this.mpaStorage = mpaStorage;
        this.usersStorage = usersStorage;
        this.likeStorage = likeStorage;
    }


    public Film addFilm(Film addedFilm) {
        log.info("FilmsService: сохраненеи фильма: {}", addedFilm);
        filmsStorage.addFilm(addedFilm);
        genresStorage.updateGenreForFilm(addedFilm);
        return addedFilm;
    }

    public Film findById(int id) {
        log.info("FilmsService: получение фильма с id: {}", id);
        Film film = filmsStorage.findById(id).orElseThrow(() -> new FilmNotFoundException("Фильм с id: " + id + " не найден"));
        film.setMpa(mpaStorage.findById(film.getMpa().getId()).get());
        film.setLikes(likeStorage.findUserIdByFilmId(id));
        film.setGenres(genresStorage.findGenresByFilmID(id));
        return film;
    }

    public List<Film> getListOfAllFilms() {
        log.info("FilmsService: получение списка всех фильмов");
        List<Film> films = filmsStorage.getListOfAllFilms();
        for (Film film : films) {
            film.setMpa(mpaStorage.findById(film.getMpa().getId()).get());
            film.setLikes(likeStorage.findUserIdByFilmId(film.getId()));
            film.setGenres(genresStorage.findGenresByFilmID(film.getId()));
        }
        return films;
    }

    public Film updateFilm(Film updatedFilm) {
        log.info("FilmsService: обновление фильма: {}", updatedFilm);
        filmsStorage.updateFilm(updatedFilm);
        genresStorage.updateGenreForFilm(updatedFilm);
        return updatedFilm;
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
        log.info("FilmsService: получение рейтинга  по id: {}", id);
        return mpaStorage.findById(id).orElseThrow(() -> new MpaNotFoundException("Рейтинг с id: " + id + " не найден"));
    }

    public List<Mpa> getListOfAllMpa() {
        log.info("FilmsService: получение списка всех рейтингов");
        return mpaStorage.getAll();
    }

    public void addLike(int filmId, int userId) { // добавление лайка
        log.info("FilmsService: добавление лайка фильму с id {} от пользователя с id {}", filmId, userId);
        filmsStorage.findById(filmId).orElseThrow(() -> new FilmNotFoundException("Фильм с id: " + filmId + " не найден"));
        usersStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId + " не найден"));
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {  // удаление лайка
        log.info("FilmsService: удаление лайка у фильма с id {} от пользователя с id {}", filmId, userId);
        filmsStorage.findById(filmId).orElseThrow(() -> new FilmNotFoundException("Фильм с id: " + filmId + " не найден"));
        usersStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId + " не найден"));
        likeStorage.removeLike(filmId, userId);
    }

    public Set<Film> findTopFilms(int count) {   // вывод 10 наиболее популярных фильмов по количеству лайков
        return getListOfAllFilms().stream()
                .sorted(Comparator.comparing(Film::getLikeCount).reversed())
                .limit(count)
                .collect(Collectors.toSet());
    }
}
