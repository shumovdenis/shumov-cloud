package ru.netology.shumovcloud.security.jwt;

import lombok.NoArgsConstructor;
import ru.netology.shumovcloud.entity.User;

@NoArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(User user){
        return new JwtUser (
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );
    }
}
