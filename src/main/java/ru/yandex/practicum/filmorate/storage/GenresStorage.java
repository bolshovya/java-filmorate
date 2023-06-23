package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenresStorage {

    Genre addGenre(Genre genre);

    public Genre findById(int id);

    public List<Genre> getListOfAllGenre();

    public Set<Genre> findGenresByFilmID(int filmId);
}
