package ru.netology.shumovcloud.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.repository.UserRepository;
import ru.netology.shumovcloud.service.UserService;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findByName(String userName) {
        User result = userRepository.findByUsername(userName);
        log.info("IN findByName - user: {} found by username : {}", result, userName);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден")) ;
        log.info("IN findById - user: {} found by ID : {}", result, id);
        return result;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getALL - {} users found", result.size());
        return result;
    }
}
