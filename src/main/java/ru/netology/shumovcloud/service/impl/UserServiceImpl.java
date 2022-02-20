package ru.netology.shumovcloud.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.repository.UserRepository;
import ru.netology.shumovcloud.service.UserService;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findByLogin(String login) {
        User result = userRepository.findByLogin(login);
        log.info("IN findByName - user: {} found by username : {}", result, login);
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

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return (UserDetails) user;
    }
}
