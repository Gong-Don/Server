package com.example.gongdon.errors.exception;

public class NotExistWriterException extends RuntimeException {
    private static final String MESSAGE = "등록되지 않은 작성자입니다.";
    public NotExistWriterException () {
        super(MESSAGE);
    }
}
