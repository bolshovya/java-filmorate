package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class Genre {

    public int id;

    public String genre;

    public Genre() {
    }

    public Genre(int id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre1 = (Genre) o;
        return id == genre1.id && Objects.equals(genre, genre1.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genre);
    }
}
