package ru.yandex.practicum.filmorate.exception;

public class ValidationFilmsException extends RuntimeException {

    public ValidationFilmsException() {

    }

    public ValidationFilmsException(String message) {
        super(message);
    }

}
