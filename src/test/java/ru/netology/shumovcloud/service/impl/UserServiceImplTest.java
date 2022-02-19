package ru.netology.shumovcloud.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.netology.shumovcloud.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @Test
    void findById() {
    }

    @Test
    void findByEmail() {

    }
}