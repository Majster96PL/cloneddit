package com.example.cloneddit.registration;

import com.example.cloneddit.clone.ClonedditException;
import com.example.cloneddit.registration.email.EmailNotification;
import com.example.cloneddit.registration.email.EmailSenderService;
import com.example.cloneddit.registration.email.MailBuilder;
import com.example.cloneddit.registration.email.token.Token;
import com.example.cloneddit.registration.email.token.TokenRepository;
import com.example.cloneddit.registration.user.User;
import com.example.cloneddit.registration.user.UserRepository;
import com.example.cloneddit.registration.user.UserRequest;
import com.example.cloneddit.web.PasswordEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Slf4j
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final MailBuilder mailBuilder;
    private final EmailSenderService emailSenderService;

    @Transactional
    public void register(UserRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUserName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.bCryptPasswordEncoder()
                .encode(request.getPassword()));
        newUser.setCreatedUser(now());
        newUser.setEnabledUser(false);

        userRepository.save(newUser);
        String url = MailBuilder.PATH_EMAIL;
        String token = generateToken(newUser);
        String message = mailBuilder.buildEmail(
                "Thanks for registration to Cloneddit,please click on the link to activate your account:  "
                + url + token );
        emailSenderService.send(
                new EmailNotification("Please Active your account", newUser.getEmail(),message));
    }

    private String generateToken(User user){
        String token = UUID.randomUUID().toString();
        Token verificationToken = new Token();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);
        return token;
    }

    public void accountVerify(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        reduceUserEnabled(tokenOptional.orElseThrow(()-> new ClonedditException("Token invalid!")));
    }

    public void reduceUserEnabled(Token token){
        String username = token.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ClonedditException("User" + username + "not found!"));
        user.setEnabledUser(true);
        userRepository.save(user);
    }
}
