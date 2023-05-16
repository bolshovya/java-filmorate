package ru.yandex.practicum.filmorate.exception;

public class ValidationUsersException extends RuntimeException {

    public ValidationUsersException() {

    }

    public ValidationUsersException(String message) {
        super(message);
    }
}
