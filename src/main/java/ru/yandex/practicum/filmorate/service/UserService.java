package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    /*
    добавление в друзья
     */
    public void addToFriends(User user1, User user2) {
        user1.addFriend(user2);
        user2.addFriend(user1);
    }

    /*
    удаление из друзей
     */
    public void removeFromFriends(User user1, User user2) {
        user1.removeFriend(user2);
        user2.removeFriend(user1);
    }

    /*
    вывод списка общих друзей
     */
    public Set<Integer> getListIdMutualFriends(User user1, User user2) {
        Set<Integer> mutualSet = new LinkedHashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet;
    }

}
