package dev.tolkach.attemptsservice.application.exception;

public class DuplicateTestAttemptException extends RuntimeException {
    public DuplicateTestAttemptException(String message) {
        super(message);
    }
}
