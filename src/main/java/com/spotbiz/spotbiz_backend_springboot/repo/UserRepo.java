package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Status;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);
    User findByUserId(int userId);
    User findByRoleAndStatus(Role role, Status status);

    Integer countUsersByRoleAndStatus(Role role, Status status);
}
