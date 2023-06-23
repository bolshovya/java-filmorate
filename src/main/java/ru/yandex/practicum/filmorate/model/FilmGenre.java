package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class FilmGenre {

    private int filmId;

    private int genreId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmGenre filmGenre = (FilmGenre) o;
        return filmId == filmGenre.filmId && genreId == filmGenre.genreId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, genreId);
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public FilmGenre() {
    }

    public FilmGenre(int filmId, int genreId) {
        this.filmId = filmId;
        this.genreId = genreId;
    }
}
