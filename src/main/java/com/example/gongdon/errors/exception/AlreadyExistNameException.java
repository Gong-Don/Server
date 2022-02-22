package com.example.gongdon.errors.exception;

public class AlreadyExistNameException extends RuntimeException {
    private static final String MESSAGE = "이미 등록된 닉네임입니다.";
    public AlreadyExistNameException () {
        super(MESSAGE);
    }
}
