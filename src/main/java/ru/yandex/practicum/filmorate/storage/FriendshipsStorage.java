package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;

public interface FriendshipsStorage {

    Friendship findById(int id);

    List<Friendship> getListOfAllFriendship();
}
