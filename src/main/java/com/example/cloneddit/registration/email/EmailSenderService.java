package com.example.cloneddit.registration.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@AllArgsConstructor
@Slf4j
public class EmailSenderService implements EmailSender {

    private final static String FAILED_MESSAGE = "Failed to send email!";
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String receiver, String email) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            messageHelper.setText(email, true);
            messageHelper.setTo(receiver);
            messageHelper.setSubject("Confirm Your Email!");
            messageHelper.setFrom("cloneddit@gmail.com");
            mailSender.send(mimeMessage);

        }catch(MessagingException e){
            log.error(FAILED_MESSAGE, e);
            throw new IllegalStateException(FAILED_MESSAGE);
        }
    }
}
