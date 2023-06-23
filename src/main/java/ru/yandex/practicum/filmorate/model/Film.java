package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Film {


    private int id;

    @NotBlank
    private String name;

    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;

    @Positive
    private int duration;

    private Set<Integer> likes = new HashSet<>();

    private Set<Genre> genres = new HashSet<>();

    private Rating mpa;

    public Film() {
    }


    public Film(String name, String description, LocalDate releaseDate, int duration, Rating mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }

    public void addLike(User user) {
        likes.add(user.getId());
    }

    public void addLike(int userId) {
        likes.add(userId);
    }

    public void removeLike(User user) {
        if (likes.contains(user.getId())) {
            likes.remove(user.getId());
        }
    }

    public void removeLike(int userId) {
        if (likes.contains(userId)) {
            likes.remove(userId);
        }
    }

    public Set<Integer> getLikes() {
        return likes;
    }

    public int getLikeCount() {
        return likes.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && Objects.equals(name, film.name) && Objects.equals(description, film.description) && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(duration, film.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, releaseDate, duration);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                '}';
    }

    public void setLikes(Set<Integer> likes) {
        this.likes = likes;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Rating getMpa() {
        return mpa;
    }

    public void setMpa(Rating mpa) {
        this.mpa = mpa;
    }
}
