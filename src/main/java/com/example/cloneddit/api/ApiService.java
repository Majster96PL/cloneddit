package com.example.cloneddit.api;

import com.example.cloneddit.api.login.LoginRequest;
import com.example.cloneddit.api.login.LoginResponse;
import com.example.cloneddit.api.login.jwt.JWTProvider;
import com.example.cloneddit.api.registration.email.EmailSender;
import com.example.cloneddit.api.registration.email.EmailSenderService;
import com.example.cloneddit.api.registration.user.*;
import com.example.cloneddit.api.registration.email.EmailValidation;
import com.example.cloneddit.api.registration.email.token.Token;
import com.example.cloneddit.api.registration.email.token.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ApiService {

    private final TokenService tokenService;
    private final EmailValidation emailValidation;
    private final UserService userService;
    private final EmailSender emailSender;
    private final EmailSenderService emailSenderService;
    private final JWTProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    public String register(UserRequest request) {
        boolean isEmailValidation = emailValidation.test(request.getEmail());

        if(!isEmailValidation){
            throw new IllegalStateException("Email isn't validation!");
        }

        String token = userService.getRecordUser(
                new User(
                        request.getFirstname(),
                        request.getLastname(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
        String url = "http://localhost:8080/cloneddit/api/register/account-verify?token=" + token;
        emailSender.send(request.getEmail(),
                emailSenderService.buildEmailSender(request.getFirstname(), url));

        return token;
    }

    @Transactional
    public String confirmToken(String token){
        Token verificationToken = tokenService
                .getToken(token)
                .orElseThrow(
                        ()-> new IllegalStateException("Token not found!")
                );

        if(verificationToken.getConfirmedDate() != null){
            throw new IllegalStateException("Email already confirmed!");
        }
        LocalDateTime expiredToken = verificationToken.getExpiredDate();

        if(expiredToken.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token expired");
        }

        tokenService.setConfirmedToken(token);
        userService.enabledUser(verificationToken.getUser().getEmail());
        return "Token confirmed!";
    }

    public LoginResponse login(LoginRequest loginRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.tokenGenerated(authenticate);
        return new LoginResponse(authenticationToken, loginRequest.getUsername());
    }

    public User getEmailAsUser() {
        org.springframework.security.core.userdetails.User principalUser =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principalUser.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User from email not found!")
                );
    }
}
