package com.example.cloneddit.registration.user;

import com.example.cloneddit.registration.email.token.Token;
import com.example.cloneddit.registration.email.token.TokenService;
import com.example.cloneddit.web.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private static final String MESSAGE = " User with email %s not found ";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(MESSAGE, email))
                );
    }

    public String getRecordUser(User user){
        boolean isUserExists = userRepository
                .findByEmail(user.getEmail()).isPresent();

        if(isUserExists){
            throw new IllegalStateException("Email taken!");
        }

        String password = passwordEncoder.bCryptPasswordEncoder()
                .encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        var newToken = new Token(
                token,
                user,
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now()

        );
        tokenService.saveToken(newToken);
        return token;
    }

    public int enabledUser(String email){
        return userRepository.enabledUser(email);
    }
}
