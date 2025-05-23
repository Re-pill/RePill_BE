package com.repill.backend.domain.token.repository;

import com.repill.backend.domain.token.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByRefreshToken(String refreshToken);

    Optional<Token> findByMemberId(Long memberId);
}