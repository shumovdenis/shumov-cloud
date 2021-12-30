package ru.netology.shumovcloud.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus
public class FileNotUniqException extends Exception {
    public FileNotUniqException(String msg) {
        super(msg);
    }
}
