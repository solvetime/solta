package com.solta.email.repository;

import com.solta.email.entity.EmailAuth;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    Optional<EmailAuth> findByEmail(String email);
    Optional<EmailAuth> findByEmailAndAuthCode(String email, String authCode);
    void deleteByExpireDateTimeBefore(LocalDateTime now);
}
