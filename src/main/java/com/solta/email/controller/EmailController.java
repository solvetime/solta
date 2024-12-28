package com.solta.email.controller;


import com.solta.email.dto.request.EmailDTO;
import com.solta.email.dto.request.AuthCodeEmailDTO;
import com.solta.email.service.EmailService;
import com.solta.member.domain.Member;
import com.solta.member.repository.MemberRepository;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
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
    public ResponseEntity<EmailDTO> existsEmail(@RequestBody @Valid EmailDTO email) {
        try {
            Member byEmail = memberRepository.findByEmail(email.email());
            return ResponseEntity.ok(email);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid EmailDTO email) {
        emailService.sendEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@Valid @RequestBody AuthCodeEmailDTO authCodeEmailDTO) {
        if (emailService.verifyEmail(authCodeEmailDTO.email(), authCodeEmailDTO.authCode())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
