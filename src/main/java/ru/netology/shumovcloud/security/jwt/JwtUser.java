package ru.netology.shumovcloud.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.netology.shumovcloud.entity.Role;

import java.util.Collection;
import java.util.Set;

public class JwtUser implements UserDetails {
    private final Long id;
    private final String userName;
    private final String password;
    private final Set<Role> roles;

    public JwtUser(Long id, String userName, String password, Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
