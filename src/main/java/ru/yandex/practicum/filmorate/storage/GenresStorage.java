package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenresStorage {

    Genre addGenre(Genre genre);

    Optional<Genre> findById(int id);
    List<Genre> getListOfAllGenre();

    Set<Genre> findGenresByFilmID(int filmId);
}
