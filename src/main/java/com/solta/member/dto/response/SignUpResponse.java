package com.solta.member.dto.response;

import com.solta.member.domain.Member;

public record SignUpResponse(
        Long id,
        String email
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(member.getId(), member.getEmail());
    }
}
