package ru.netology.shumovcloud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(FileNotUniqException.class)
    public ResponseEntity<String> handleIC (FileNotUniqException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getLocalizedMessage());
    }
}
