package ru.netology.shumovcloud.dto;

import lombok.Getter;

@Getter
public class NewFileName {
    private String filename;

    public NewFileName (String filename) {
        this.filename = filename;
    }
}
