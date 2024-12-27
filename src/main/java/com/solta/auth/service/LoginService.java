package com.solta.auth.service;

import com.solta.auth.dto.AuthInfo;
import com.solta.auth.dto.LoginRequest;
import com.solta.member.domain.Member;
import com.solta.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;

    public AuthInfo login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email());
        return new AuthInfo(member.getEmail(), member.getName());
    }
}
