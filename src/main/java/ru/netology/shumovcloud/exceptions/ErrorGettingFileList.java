package ru.netology.shumovcloud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ErrorGettingFileList extends RuntimeException{
    public ErrorGettingFileList(String message) {
        super(message);
    }
}
