package com.supermarketsystem.supermarket_system_g8_1_03.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.supermarketsystem.supermarket_system_g8_1_03.models.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    // We need this to check if a user exists when they try to log in
    Optional<Users> findByEmail(String email);
}