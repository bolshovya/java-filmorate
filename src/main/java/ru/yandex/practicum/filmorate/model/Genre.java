package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class Genre {

    public int filmId;

    public String genre;

    public Genre() {
    }

    public Genre(int filmId, String genre) {
        this.filmId = filmId;
        this.genre = genre;
    }


    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre1 = (Genre) o;
        return filmId == genre1.filmId && Objects.equals(genre, genre1.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, genre);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "filmId=" + filmId +
                ", genre='" + genre + '\'' +
                '}';
    }
}
