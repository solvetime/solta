package com.solta.member.service;

import com.solta.global.util.EncryptUtils;
import com.solta.member.controller.SignUpDTO;
import com.solta.member.domain.Member;
import com.solta.member.repository.MemberRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EncryptUtils encryptUtils;

    public Member signUp(SignUpDTO signupDTO) {
        String encryptPassword = encryptUtils.encrypt(signupDTO.password());

        return memberRepository.save(Member.builder()
                .name(signupDTO.name())
                .email(signupDTO.email())
                .password(encryptPassword)
                .build());
    }

    public boolean isExistEmail(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            return false;
        }

        return true;
    }
}
