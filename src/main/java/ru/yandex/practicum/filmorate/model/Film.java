package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(Genre::getId));

    private Mpa mpa;


    public Film(String name, String description, LocalDate releaseDate, int duration, Mpa mpa) {
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


    public void setLikes(Set<Integer> likes) {
        this.likes = likes;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Mpa getMpa() {
        return mpa;
    }

    public void setMpa(Mpa mpa) {
        this.mpa = mpa;
    }
}
