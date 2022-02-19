package ru.netology.shumovcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.netology.shumovcloud.config.jwt.JwtTokenProvider;
import ru.netology.shumovcloud.dto.Login;
import ru.netology.shumovcloud.exceptions.ErrorBadCredentials;


@Service
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String getToken(Login login) {
        try {
            Authentication authResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword()));
            String token = jwtTokenProvider.createToken(login.getLogin());
           log.info("Token created successfully for "+login.getLogin());
            return token;
        } catch (AuthenticationException e) {
            log.info("Bad credentials for "+login.getLogin());
            throw new ErrorBadCredentials("Bad credentials!");
        }
    }

    public Boolean removeToken(String authToken) {
        String userName = jwtTokenProvider.getUserName(authToken.substring(7));
        return true;
    }}
