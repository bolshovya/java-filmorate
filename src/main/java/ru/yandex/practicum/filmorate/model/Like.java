package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class Like {
    private int film_id;
    private int user_id;

    public Like() {
    }

    public Like(int film_id, int user_id) {
        this.film_id = film_id;
        this.user_id = user_id;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return film_id == like.film_id && user_id == like.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(film_id, user_id);
    }

    @Override
    public String toString() {
        return "Like{" +
                "film_id=" + film_id +
                ", user_id=" + user_id +
                '}';
    }
}
