package com.solta.email.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "email_auth")
@NoArgsConstructor
@Getter
public class EmailAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String authCode;

    @NotNull
    private LocalDateTime expireDateTime;

    @Builder
    public EmailAuth(String email, String authCode, LocalDateTime expireDateTime) {
        this.email = email;
        this.authCode = authCode;
        this.expireDateTime = expireDateTime;
    }
}
