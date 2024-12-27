package com.solta.email.controller;


import com.solta.email.dto.EmailDTO;
import com.solta.email.dto.VerificationEmailDTO;
import com.solta.email.service.MailService;
import com.solta.global.util.RedisUtil;
import jakarta.validation.Valid;
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

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid EmailDTO email) {
        mailService.sendEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@Valid @RequestBody VerificationEmailDTO verificationEmailDTO) {
        if (mailService.verifyEmail(verificationEmailDTO)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
