package com.solta.email.controller;


import com.solta.email.dto.request.EmailDTO;
import com.solta.email.dto.request.AuthCodeEmailDTO;
import com.solta.email.dto.response.EmailWithAuthCodeDTO;
import com.solta.email.service.EmailService;
import com.solta.member.domain.Member;
import com.solta.member.repository.MemberRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<EmailDTO> existsEmail(@Valid @RequestBody EmailDTO email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email.email());

        if (byEmail.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(email);
        }

        return ResponseEntity.ok(email);
    }

    @PostMapping("/send")
    public ResponseEntity<EmailWithAuthCodeDTO> sendEmail(@Valid @RequestBody EmailDTO email) {
        EmailWithAuthCodeDTO result = emailService.sendEmail(email).join();// 이거 해결 ㄱ

        return ResponseEntity.ok(result);
    }

    @PostMapping("/verify")
    public ResponseEntity<EmailWithAuthCodeDTO> verifyEmail(@Valid @RequestBody AuthCodeEmailDTO authCodeEmailDTO) {
        EmailWithAuthCodeDTO responseData = new EmailWithAuthCodeDTO(authCodeEmailDTO.email(), authCodeEmailDTO.authCode());

        if (emailService.verifyEmail(authCodeEmailDTO.email(), authCodeEmailDTO.authCode())) {
            return ResponseEntity.ok(responseData);
        }
        return ResponseEntity.badRequest()
                .body(responseData);
    }
}
