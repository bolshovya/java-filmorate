package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmsStorage {

    public Film addFilm(Film addedFilm);

    public Film updateFilm(Film updatedFilm);

    public Film removeFilmFromStorage(Film removedFilm);

    public Film findById(int id);

    public List<Film> getListOfAllFilms();

    public int getSizeStorage();

}
