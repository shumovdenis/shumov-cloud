package ru.netology.shumovcloud.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.netology.shumovcloud.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();

    User findByLogin(String login);

    User findById(Long id);
}
