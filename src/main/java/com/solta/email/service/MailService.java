package com.solta.email.service;

import com.solta.email.dto.EmailDTO;
import com.solta.email.exception.EmailSendFailureException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    public static final String MAIL_SUBJECT = "[Solta] 이메일 인증 코드를 발송해 드립니다.";
    private static final long EXPIRE_PERIOD = 1000L * 60L * 30;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(EmailDTO to) {
        String randomCode = createRandomCode();
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(to.email());
            mimeMessageHelper.setSubject(MAIL_SUBJECT);
            mimeMessageHelper.setText(randomCode);
            javaMailSender.send(mimeMessage);

            log.info("이메일 전송 성공 to={}, message={}", to.email(), randomCode);
        } catch (MessagingException e) {
            log.info("이메일 전송 실패 to={}, message={}", to.email(), e.getMessage());
            throw new EmailSendFailureException();
        }
    }

    @Transactional
    public boolean verifyEmail(String email, String authCode) {
        try {
            String code = "";
            return authCode.equals(code);
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
    }

    private String createRandomCode() {
        String alphabet = "ABCDEFGHIJKLNMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String number = "1234567890";
        StringBuilder sb = new StringBuilder();

        for (int cnt = 0; cnt < 6; cnt++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(36);
            if (randomNumber < 24) {
                int idx = ThreadLocalRandom.current().nextInt(alphabet.length());
                sb.append(alphabet.charAt(idx));
                continue;
            }

            int idx = ThreadLocalRandom.current().nextInt(number.length());
            sb.append(number.charAt(idx));
        }

        return sb.toString();
    }
}
