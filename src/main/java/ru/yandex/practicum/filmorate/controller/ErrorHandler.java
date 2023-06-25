package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.debug("Ошибка валидации данных. Получен статус 400 {}", e.getMessage());
        return new ErrorResponse("Ошибка валидации фильма.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationFilmsException(final ValidationFilmsException e) {
        log.debug("Ошибка валидации фильма. Получен статус 400 {}", e.getMessage());
        return new ErrorResponse("Ошибка валидации фильма.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationUsersException(final ValidationUsersException e) {
        log.debug("Ошибка валидации пользователя. Получен статус 400 {}", e.getMessage());
        return new ErrorResponse("Ошибка валидации пользователя.", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotFoundException(final FilmNotFoundException e) {
        log.debug("Фильм не найден. Получен статус 404 {}", e.getMessage());
        return new ErrorResponse("FilmNotFoundException:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e) {
        log.debug("Пользователь не найден. Получен статус 404 {}", e.getMessage());
        return new ErrorResponse("UserNotFoundException:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMpaNotFoundException(final MpaNotFoundException e) {
        log.debug("Рейтинг не найден. Получен статус 404 {}", e.getMessage());
        return new ErrorResponse("MpaNotFoundException:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleManagerException(final ManagerException e) {
        log.debug("Получен статус 404 {}", e.getMessage());
        return new ErrorResponse("ManagerException:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGenreNotFoundException(final GenreNotFoundException e) {
        log.debug("Жанр не найден. Получен статус 404 {}", e.getMessage());
        return new ErrorResponse("GenreNotFoundException:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.debug("Получен статус 500 {}", e.getMessage(), e);
        return new ErrorResponse("Произошла непредвиденная ошибка.", e.getMessage());
    }

}
