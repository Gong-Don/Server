package com.example.gongdon.errors.exception;

public class NotMatchPasswordException extends RuntimeException {
    private static final String MESSAGE = "아이디 또는 비밀번호가 일치하지 않습니다.";
    public NotMatchPasswordException () {
        super(MESSAGE);
    }
}
