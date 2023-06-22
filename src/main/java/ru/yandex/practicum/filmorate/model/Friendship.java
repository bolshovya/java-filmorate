package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class Friendship {

    private int userId;

    private int friendId;
    private boolean confirmation;

    public Friendship() {
    }

    public Friendship(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public Friendship(int userId, int friendId, boolean confirmation) {
        this.userId = userId;
        this.friendId = friendId;
        this.confirmation = confirmation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return userId == that.userId && friendId == that.friendId && confirmation == that.confirmation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId, confirmation);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", confirmation=" + confirmation +
                '}';
    }
}
