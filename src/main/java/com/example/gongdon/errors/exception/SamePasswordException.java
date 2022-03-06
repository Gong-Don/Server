package com.example.gongdon.errors.exception;

public class SamePasswordException extends RuntimeException {
    private static final String MESSAGE = "새로운 비밀번호가 현재 비밀번호와 같습니다";
    public SamePasswordException () {
        super(MESSAGE);
    }
}
