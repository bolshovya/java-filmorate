package ru.yandex.practicum.filmorate.model;


import java.util.Objects;

public class Like {
    private int filmId;
    private int userId;


    public Like() {
    }

    public Like(int filmId, int userId) {
        this.filmId = filmId;
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return filmId == like.filmId && userId == like.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, userId);
    }

    @Override
    public String toString() {
        return "Like{" +
                "filmId=" + filmId +
                ", userId=" + userId +
                '}';
    }
}
