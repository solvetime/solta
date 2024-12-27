package com.solta.member.controller;

import jakarta.validation.constraints.Pattern;

public record SignupDTO(
        @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "올바른 이메일 형식이 아닙니다.")
        String email,
        String name,
        String password
) {
}
