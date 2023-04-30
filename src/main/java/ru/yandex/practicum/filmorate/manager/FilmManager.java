package ru.yandex.practicum.filmorate.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

public class FilmManager {
    private static final Logger log = LoggerFactory.getLogger(FilmManager.class);
    private final Map<Integer, Film> filmStorage = new HashMap<>();
    private static int idCount;


    public int setId() {
        return idCount++;
    }

    public int getId() {
        return idCount;
    }

    public Film validation(Film addedFilm) throws ValidationException {
        Optional<String> nameOpt = Optional.of(addedFilm.getName());
        if (addedFilm.getName().isBlank() || nameOpt.isEmpty()) {
            throw new ValidationException("Added film doesn't contains name.");
        }
        if (addedFilm.getDescription().toCharArray().length > 200) {
            throw new ValidationException("The description of the added film contains more than 200 chars.");
        }
        if (addedFilm.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Release date not earlier than 28 December 1895.");
        }
        if (addedFilm.getDuration().isNegative() || addedFilm.getDuration().isZero()) {
            throw new ValidationException("Duration can't be negative or zero.");
        }
        return addedFilm;
    }

    public Film addFilm(Film addedFilm) throws ValidationException {
        Film film = validation(addedFilm);
        film.setId(setId());
        filmStorage.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film updatedFilm) throws ManagerException {
        Optional<Integer> filmIdOpt = Optional.of(updatedFilm.getId());
        if (filmIdOpt.isEmpty() || !filmStorage.containsKey(updatedFilm.getId())) {
            throw new ManagerException("Added film not listed.");
        }
        filmStorage.put(updatedFilm.getId(), updatedFilm);
        return updatedFilm;
    }

    public Film removeFilmFromStorage(Film removedFilm) {
        filmStorage.remove(removedFilm.getId());
        return removedFilm;
    }

    public Film findById(int id) throws ManagerException {
        if (!filmStorage.containsKey(id)) {
            log.error("ManagerException: film with this id not found");
            throw new ManagerException("Film with this id not found.");
        } else {
            return filmStorage.get(id);
        }
    }

    public List<Film> getListOfAllFilms() {
        return new ArrayList<>(filmStorage.values());
    }

    public int getSizeStorage() {
        return filmStorage.size();
    }

}
