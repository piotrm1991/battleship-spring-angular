package com.example.battleship.security.repository;

import com.example.battleship.security.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on RefreshToken entities.
 */
@Repository
//@Transactional("tm1")
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);

  Optional<RefreshToken> findByUserId(Long userId);
}
