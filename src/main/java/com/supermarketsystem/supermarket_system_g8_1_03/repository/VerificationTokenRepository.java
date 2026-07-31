package com.supermarketsystem.supermarket_system_g8_1_03.repository;

import com.supermarketsystem.supermarket_system_g8_1_03.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}