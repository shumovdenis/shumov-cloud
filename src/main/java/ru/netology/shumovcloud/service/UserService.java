package ru.netology.shumovcloud.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.repository.UserRepository;

@Service
@NoArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;

}
