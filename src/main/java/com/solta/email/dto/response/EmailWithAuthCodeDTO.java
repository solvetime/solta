package com.solta.email.dto.response;

public record EmailWithAuthCodeDTO(
        String email,
        String authCode
) {
}
