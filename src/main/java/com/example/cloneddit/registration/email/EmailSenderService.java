package com.example.cloneddit.registration.email;

import com.example.cloneddit.clone.ClonedditException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailSenderService implements EmailSender {

    private final static Logger log = LoggerFactory.getLogger(EmailSenderService.class);
    private static final String ERROR_MESSAGE = "Error during send email: ";
    private final JavaMailSender mailSender;
    private final MailBuilder mailBuilder;

    @Override
    @Async
    public void send(EmailNotification emailNotification) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("cloneddit@gmail.com");
            messageHelper.setTo(emailNotification.getRecipient());
            messageHelper.setSubject(emailNotification.getSubject());
            messageHelper.setText(mailBuilder.buildEmail(emailNotification.getBody()));
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Email activation sent!");
        }catch(MailException e){
            log.error("Error during send email", e);
            throw new ClonedditException(ERROR_MESSAGE + emailNotification.getRecipient(), e);
        }
    }
}
