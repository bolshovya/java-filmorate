package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationFilmsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmsStorage;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmsStorage implements FilmsStorage {
    private final Map<Integer, Film> filmsStorage = new HashMap<>();
    private static int FILMS_COUNT;


    public int setId() {
        return ++FILMS_COUNT;
    }

    public Film validation(Film addedFilm) {
        if (addedFilm.getName().isBlank()) {
            throw new ValidationFilmsException("Added film doesn't contains name.");
        }
        if (addedFilm.getDescription().toCharArray().length > 200) {
            throw new ValidationFilmsException("The description of the added film contains more than 200 chars.");
        }
        if (addedFilm.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationFilmsException("Release date not earlier than 28 December 1895.");
        }
        if (addedFilm.getDuration() <= 0) {
            throw new ValidationFilmsException("Duration can't be negative or zero.");
        }
        return addedFilm;
    }

    public Film addFilm(Film addedFilm) throws ValidationFilmsException {
        Film film = validation(addedFilm);
        film.setId(setId());
        filmsStorage.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film updatedFilm) throws FilmNotFoundException {
        Optional<Integer> filmIdOpt = Optional.of(updatedFilm.getId());
        if (filmIdOpt.isEmpty() || !filmsStorage.containsKey(updatedFilm.getId())) {
            throw new FilmNotFoundException();
        }
        filmsStorage.put(updatedFilm.getId(), updatedFilm);
        return updatedFilm;
    }

    public Film removeFilmFromStorage(Film removedFilm) {
        filmsStorage.remove(removedFilm.getId());
        return removedFilm;
    }

    public Film findById(int id) throws FilmNotFoundException {
        if (!filmsStorage.containsKey(id)) {
            log.error("FilmNotFoundException: film with this id not found");
            throw new FilmNotFoundException();
        } else {
            return filmsStorage.get(id);
        }
    }

    public List<Film> getListOfAllFilms() {
        return new ArrayList<>(filmsStorage.values());
    }

    public int getSizeStorage() {
        return filmsStorage.size();
    }

}
