package com.example.ThymeleafJpaProject.repositories;

import com.example.ThymeleafJpaProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername (String username);
}
