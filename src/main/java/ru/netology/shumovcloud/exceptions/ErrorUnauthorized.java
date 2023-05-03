package ru.netology.shumovcloud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ErrorUnauthorized extends RuntimeException {
    public ErrorUnauthorized(String msg) {
        super(msg);
    }
}
