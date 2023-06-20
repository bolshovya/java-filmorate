package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenresStorage {

    public Genre findById(int id);

    public List<Genre> getListOfAllGenre();
}
