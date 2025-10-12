package com.example.blog_platform.repository;

import com.example.blog_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Boolean existsByUserName(String userName);

    public Boolean existsByEmail(String email);

    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);
}
