package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class Rating {
    private int filmId;

    private String rating;


    public Rating() {
    }

    public Rating(int filmId, String rating) {
        this.filmId = filmId;
        this.rating = rating;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return filmId == rating1.filmId && Objects.equals(rating, rating1.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, rating);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "filmId=" + filmId +
                ", rating='" + rating + '\'' +
                '}';
    }

}
