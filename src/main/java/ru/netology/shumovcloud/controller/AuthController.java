package ru.netology.shumovcloud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.shumovcloud.dto.Login;
import ru.netology.shumovcloud.dto.Token;
import ru.netology.shumovcloud.service.AuthService;

import javax.security.auth.message.AuthException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Login login) throws AuthException {
       String res = authService.getToken(login);
       return new ResponseEntity<>( new Token(res), HttpStatus.OK);
    }

}
