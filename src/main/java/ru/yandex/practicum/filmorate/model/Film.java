package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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

    private int likeCount;

    private Set<Integer> likers;

    public Film() {

    }

    public Film(String name, String description, String releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.duration = duration;
        this.likers = new LinkedHashSet<>();
    }

    public void addLiker(User user) {
        likers.add(user.getId());
        ++likeCount;
    }

    public void addLiker(Integer userId) {
        likers.add(userId);
        ++likeCount;
    }

    public void removeLiker(User user) {
        if (likers.contains(user.getId())) {
            likeCount--;
            likers.remove(user.getId());
        }
    }

    public void removeLiker(Integer userId) {
        if (likers.contains(userId)) {
            likeCount--;
            likers.remove(userId);
        }
    }

    public int getLikeCount() {
        return likeCount;
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
