package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.*;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmsStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmsService {

    private FilmsStorage filmsStorage;

    @Autowired
    public FilmsService() {
        this.filmsStorage = new InMemoryFilmsStorage();
    }

    public List<Film> getListOfAllFilms() {
        return filmsStorage.getListOfAllFilms();
    }

    public int getSizeStorage() {
        return filmsStorage.getSizeStorage();
    }

    public Film addFilm(Film addedFilm) {
        return filmsStorage.addFilm(addedFilm);
    }

    public Film updateFilm(Film updatedFilm) {
        return filmsStorage.updateFilm(updatedFilm);
    }

    public Film findById(int id) {
        return filmsStorage.findById(id);
    }

    public Film addLiker(int filmId, int userId) { // добавление лайка
        Film searchedFilm = filmsStorage.findById(filmId);
        searchedFilm.addLiker(userId);
        return filmsStorage.updateFilm(searchedFilm);
    }

    public void removeLiker(int filmId, int userId) {  // удаление лайка
        if (filmId < 0 || userId < 0) {
            throw new ManagerException("Параметр id имеет отрицательное значение.");
        }
        Film searchedFilm = filmsStorage.findById(filmId);
        searchedFilm.removeLiker(userId);
        filmsStorage.updateFilm(searchedFilm);
    }

    public Set<Film> findTopFilms(int count) {   // вывод 10 наиболее популярных фильмов по количеству лайков
        return filmsStorage.getListOfAllFilms().stream()
                .sorted(Comparator.comparing(Film::getLikeCount).reversed())
                .limit(count)
                .collect(Collectors.toSet());
    }
}
