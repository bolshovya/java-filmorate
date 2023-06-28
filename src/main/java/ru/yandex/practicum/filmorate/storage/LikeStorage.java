package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface LikeStorage {

    void addLike(int filmId, int userId);

    Optional<Like> findById(int id);

    List<Like> getListOfAllLikes();

    Set<Integer> findUserIdByFilmId(int filmId);

    void removeLike(int filmId, int userId);
}
