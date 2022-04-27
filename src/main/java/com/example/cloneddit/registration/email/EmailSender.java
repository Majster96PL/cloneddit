package com.example.cloneddit.registration.email;

public interface EmailSender {
    void send(String receiver, String message);
}
