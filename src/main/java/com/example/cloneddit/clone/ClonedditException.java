package com.example.cloneddit.clone;

public class ClonedditException extends RuntimeException {

    public ClonedditException(String message, Exception exception) {
        super(message, exception);
    }

    public ClonedditException(String message) {
        super(message);
    }
}
