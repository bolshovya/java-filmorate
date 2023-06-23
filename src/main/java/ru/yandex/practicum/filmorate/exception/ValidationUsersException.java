package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;

public class ValidationUsersException extends RuntimeException {

    public ValidationUsersException(String message) {
        super(message);
    }
}
