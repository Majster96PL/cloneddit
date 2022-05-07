package com.example.cloneddit.api.registration.email;

public interface EmailSender {
    void send(String receiver, String email);
}
