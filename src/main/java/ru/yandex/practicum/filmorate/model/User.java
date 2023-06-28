package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String login;

    private String name;

    @Past
    private LocalDate birthday;

    private Set<User> friends = new HashSet<>();

    private Set<Integer> friendsId = new HashSet<>();

    private Set<Integer> films = new HashSet<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addFriend(User user) {
        friends.add(user);
    }

    public void addFriendById(Integer userId) {
        friendsId.add(userId);
    }

    public void removeFriend(User user) {
        friends.remove(user.getId());
    }

    public void removeFriend(Integer userId) {
        friends.remove(userId);
    }


    public Set<Integer> getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(Set<Integer> friendsId) {
        this.friendsId = friendsId;
    }

    public Set<Integer> getFilms() {
        return films;
    }

    public void setFilms(Set<Integer> films) {
        this.films = films;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
}
