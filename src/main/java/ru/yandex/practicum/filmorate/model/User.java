package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

    private Set<Integer> friends;

    public User() {

    }

    public User(String login, String name, String email, String birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = LocalDate.parse(birthday);
        this.friends = new LinkedHashSet<>();
    }

    public void addFriend(User user) {
        friends.add(user.getId());
    }

    public void addFriend(Integer userId) {
        friends.add(userId);
    }

    public void removeFriend(User user) {
        friends.remove(user.getId());
    }

    public void removeFriend(Integer userId) {
        friends.remove(userId);
    }

    public Set<Integer> getFriends() {
        return friends;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, login, name, birthday);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
