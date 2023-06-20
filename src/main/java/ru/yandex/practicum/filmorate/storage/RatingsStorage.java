package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface RatingsStorage {

    public Rating findById(int id);

    public List<Rating> getListOfAllRatings();
}
