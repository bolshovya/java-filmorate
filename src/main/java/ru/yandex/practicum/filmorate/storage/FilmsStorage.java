package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmsStorage {

    public Film addFilm(Film addedFilm);

    public Film findById(int id);

    public List<Film> getListOfAllFilms();

    public Film updateFilm(Film updatedFilm);

    public Film removeFilmFromStorage(Film removedFilm);

    public int getSizeStorage();

}
