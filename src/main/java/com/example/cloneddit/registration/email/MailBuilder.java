package com.example.cloneddit.registration.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailBuilder {

    public static final String PATH_EMAIL = "http://localhost:8080/api/verificationAccount";
    private final TemplateEngine templateEngine;

    public String buildEmail(String message){
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("template", context);
    }
}
