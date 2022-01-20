package ru.netology.shumovcloud.service;

import ru.netology.shumovcloud.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);
}
