package com.example.gongdon.user.repository;

import com.example.gongdon.user.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findById(String tokenId);
}
