package ru.yandex.practicum.filmorate.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    public Film() {

    }

    public Film(String name, String description, String releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.duration = duration;
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
}