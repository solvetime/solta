package com.solta.member.controller;

import com.solta.email.service.EmailService;
import com.solta.global.common.response.ApiResponse;
import com.solta.global.common.response.HttpMessage;
import com.solta.member.domain.Member;
import com.solta.member.dto.response.SignUpResponse;
import com.solta.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signup(@RequestBody SignUpDTO signUpDTO) {
        if (emailService.verifyEmail(signUpDTO.email(), signUpDTO.authCode()) &&
                !memberService.isExistEmail(signUpDTO.email())) {
            Member member = memberService.signUp(signUpDTO);
            return ApiResponse.<SignUpResponse>builder()
                    .message(HttpMessage.SUCCESS.getMsg())
                    .data(SignUpResponse.from(member))
                    .build()
                    .toEntity(HttpStatus.OK);
        }

        return ApiResponse.<SignUpResponse>builder()
                .message(HttpMessage.ERROR.getMsg())
                .build()
                .toEntity(HttpStatus.BAD_REQUEST);
    }
}
