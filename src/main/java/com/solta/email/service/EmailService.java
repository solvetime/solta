package com.solta.email.service;

import com.solta.email.dto.request.EmailDTO;
import com.solta.email.entity.EmailAuth;
import com.solta.email.exception.EmailSendFailureException;
import com.solta.email.repository.EmailAuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    public static final String MAIL_SUBJECT = "[Solta] 이메일 인증 코드를 발송해 드립니다.";
    private static final long EXPIRE_PERIOD = 30;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final EmailAuthRepository emailAuthRepository;

    @Async
    public void sendEmail(EmailDTO to) {
        String randomAuthCode = createRandomCode();
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(to.email());
            mimeMessageHelper.setSubject(MAIL_SUBJECT);
            mimeMessageHelper.setText(randomAuthCode);
            javaMailSender.send(mimeMessage);

            log.info("이메일 전송 성공 to={}, message={}", to.email(), randomAuthCode);
            emailAuthRepository.save(EmailAuth.builder()
                    .email(to.email())
                    .authCode(randomAuthCode)
                    .expireDateTime(LocalDateTime.now().plusMinutes(EXPIRE_PERIOD))
                    .build());
        } catch (MessagingException e) {
            log.info("이메일 전송 실패 to={}, message={}", to.email(), e.getMessage());
            throw new EmailSendFailureException();
        }
    }

    @Transactional
    public boolean verifyEmail(String email, String authCode) {
        Optional<EmailAuth> authEmail = emailAuthRepository.findByEmailAndAuthCode(email, authCode);
        if (authEmail.isEmpty()) {
            throw new NoSuchElementException();
        }

        String code = authEmail.get().getAuthCode();
        LocalDateTime expire = authEmail.get().getExpireDateTime();
        if (expire.isAfter(LocalDateTime.now())) {
            return false;
        }

        return authCode.equals(code);
    }

    @Transactional
    @Scheduled(cron = "0 0 12 * * ?") // 매일 자정 만료
    public void deleteAfterExpireAuthCodes() {
        emailAuthRepository.deleteByExpireDateTimeBefore(LocalDateTime.now());
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
