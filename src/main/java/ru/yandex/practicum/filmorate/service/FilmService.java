package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilmService {

    private List<Film> filmList = new ArrayList<>();

    /*
    добавление лайка
     */
    public void addLiker(Film film, User user) {
        film.addLiker(user);
        filmList.add(film);
    }

    /*
    удаление лайка
     */
    public void removeLiker(Film film, User user) {
        film.removeLiker(user);
    }

    /*
    вывод 10 наиболее популярных фильмов по количеству лайков.
     */
    public Set<Film> returnTop10Films() {
        return filmList.stream()
                .sorted(Comparator.comparing(Film::getLikeCount).reversed())
                .limit(10)
                .collect(Collectors.toSet());
    }
}
