package com.solta.email.controller;


import com.solta.email.dto.request.EmailDTO;
import com.solta.email.dto.request.AuthCodeEmailDTO;
import com.solta.email.dto.response.EmailWithAuthCodeDTO;
import com.solta.email.service.EmailService;
import com.solta.global.common.response.ApiResponse;
import com.solta.global.common.response.HttpMessage;
import com.solta.member.domain.Member;
import com.solta.member.repository.MemberRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final MemberRepository memberRepository;

    @PostMapping("/exists")
    public ResponseEntity<ApiResponse<EmailDTO>> existsEmail(@Valid @RequestBody EmailDTO email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email.email());

        if (byEmail.isEmpty()) {
            return ApiResponse.<EmailDTO>builder()
                    .message(HttpMessage.ERROR.getMsg())
                    .data(email)
                    .build()
                    .toEntity(HttpStatus.BAD_REQUEST);
        }

        return ApiResponse.<EmailDTO>builder()
                .message(HttpMessage.SUCCESS.getMsg())
                .data(email)
                .build()
                .toEntity(HttpStatus.OK);
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<EmailWithAuthCodeDTO>> sendEmail(@Valid @RequestBody EmailDTO email) {
        EmailWithAuthCodeDTO result = emailService.sendEmail(email).join();// 이거 해결 ㄱ

        return ApiResponse.<EmailWithAuthCodeDTO>builder()
                .message(HttpMessage.SUCCESS.getMsg())
                .data(result)
                .build()
                .toEntity(HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<EmailWithAuthCodeDTO>> verifyEmail(@Valid @RequestBody AuthCodeEmailDTO authCodeEmailDTO) {
        if (emailService.verifyEmail(authCodeEmailDTO.email(), authCodeEmailDTO.authCode())) {
            return ApiResponse.<EmailWithAuthCodeDTO>builder()
                    .message(HttpMessage.SUCCESS.getMsg())
                    .data(new EmailWithAuthCodeDTO(authCodeEmailDTO.email(), authCodeEmailDTO.authCode()))
                    .build()
                    .toEntity(HttpStatus.OK);
        }
        return ApiResponse.<EmailWithAuthCodeDTO>builder()
                .message(HttpMessage.ERROR.getMsg())
                .data(new EmailWithAuthCodeDTO(authCodeEmailDTO.email(), authCodeEmailDTO.authCode()))
                .build()
                .toEntity(HttpStatus.BAD_REQUEST);
    }
}
