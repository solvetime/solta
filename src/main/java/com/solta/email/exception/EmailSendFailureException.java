package com.solta.email.exception;

public class EmailSendFailureException extends RuntimeException {

    private static final String ERR_MSG = "[ERROR] 인증 메일 발송에 실패했습니다.";

    public EmailSendFailureException() {
        super(ERR_MSG);
    }
}
