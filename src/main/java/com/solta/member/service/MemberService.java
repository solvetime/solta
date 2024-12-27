package com.solta.member.service;

import com.solta.global.util.EncryptUtils;
import com.solta.member.controller.SignupDTO;
import com.solta.member.domain.Member;
import com.solta.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EncryptUtils encryptUtils;

    public void signUp(SignupDTO signupDTO) {
        String encryptPassword = encryptUtils.encrypt(signupDTO.password());

        memberRepository.save(Member.builder()
                .name(signupDTO.name())
                .email(signupDTO.email())
                .password(encryptPassword)
                .build());
    }
}
