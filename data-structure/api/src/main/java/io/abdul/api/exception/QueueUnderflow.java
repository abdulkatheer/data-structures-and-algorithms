package io.abdul.api.exception;

public class QueueUnderflow extends RuntimeException {
    public QueueUnderflow(String message) {
        super(message);
    }
}
