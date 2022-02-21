package ru.kulikov.saula.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserUpdateException extends RuntimeException {

    public UserUpdateException() {
    }

    public UserUpdateException(String message) {
        super(message);
    }
}