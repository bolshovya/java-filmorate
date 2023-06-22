package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class Rating {
    private int id;

    private String rating;


    public Rating() {
    }

    public Rating(int id, String rating) {
        this.id = id;
        this.rating = rating;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == rating1.id && Objects.equals(rating, rating1.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rating='" + rating + '\'' +
                '}';
    }
}
