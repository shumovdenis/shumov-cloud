package ru.netology.shumovcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Login implements Serializable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String password;
}
