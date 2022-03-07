package ru.netology.shumovcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.shumovcloud.entity.Role;

import java.util.List;

public class RoleRepository extends JpaRepository<Role, String> {
    List<Role> findByUserNa
}
