package ru.netology.shumovcloud.security.jwt2;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {

    private String login;
    private String password;
}
