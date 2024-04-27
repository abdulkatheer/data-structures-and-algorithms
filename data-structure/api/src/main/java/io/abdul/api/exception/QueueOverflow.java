package io.abdul.api.exception;

public class QueueOverflow extends RuntimeException {
    public QueueOverflow(String message) {
        super(message);
    }
}
