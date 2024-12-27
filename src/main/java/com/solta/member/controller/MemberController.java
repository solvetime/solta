package com.solta.member.controller;

import com.solta.email.service.MailService;
import com.solta.member.domain.Member;
import com.solta.member.dto.response.SignUpResponse;
import com.solta.member.repository.MemberRepository;
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
    private final MailService mailService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpDTO signUpDTO) {
        if (mailService.verifyEmail(signUpDTO.email(), signUpDTO.authCode()) &&
                !memberService.isExistEmail(signUpDTO.email())) {
            Member member = memberService.signUp(signUpDTO);
            return ResponseEntity.ok(SignUpResponse.from(member));
        }

        return ResponseEntity.badRequest().build();
    }
}
