package com.solta.member.controller;

import com.solta.member.domain.Member;
import com.solta.member.repository.MemberJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/member")
@Transactional
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupDTO signupDTO) {
        memberJpaRepository.save(Member.builder()
                .name(signupDTO.name())
                .email(signupDTO.email())
                .password(signupDTO.password())
                .build());
        return ResponseEntity.ok().build();
    }
}
