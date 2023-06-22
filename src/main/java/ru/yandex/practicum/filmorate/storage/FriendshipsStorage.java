package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;

public interface FriendshipsStorage {

    public Friendship findById(int id);

    public List<Friendship> getListOfAllFriendship();
}
