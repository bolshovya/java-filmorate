package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;

public class GenreNotFoundException extends RuntimeException{


    public GenreNotFoundException() {
    }

    public GenreNotFoundException(String message) {
        super(message);
    }

}
