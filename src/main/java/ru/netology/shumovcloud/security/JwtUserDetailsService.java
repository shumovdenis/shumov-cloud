package ru.netology.shumovcloud.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.security.jwt.JwtUser;
import ru.netology.shumovcloud.security.jwt.JwtUserFactory;
import ru.netology.shumovcloud.service.UserService;


@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByName(username);

        if(user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserBYUsername - user with username: {} successfully load", username);

        return jwtUser;
    }
}
