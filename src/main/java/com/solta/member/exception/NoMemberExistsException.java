package com.solta.member.exception;

public class NoMemberExistsException extends RuntimeException {

    private static final String MSG = "[ERROR] 유저 정보가 존재하지 않습니다.";

    public NoMemberExistsException() {
        super(MSG);
    }
}
