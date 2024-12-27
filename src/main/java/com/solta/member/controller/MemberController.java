package com.solta.member.controller;

import com.solta.member.domain.Member;
import com.solta.member.repository.MemberJpaRepository;
import com.solta.member.service.MemberService;
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

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody SignupDTO signupDTO) {
        Long memberId = memberService.signUp(signupDTO);
        return ResponseEntity.ok().body(memberId);
    }
}
