package com.example.cloneddit.registration;

import com.example.cloneddit.registration.user.User;
import com.example.cloneddit.registration.user.UserRepository;
import com.example.cloneddit.registration.user.UserRequest;
import com.example.cloneddit.web.PasswordEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Slf4j
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        String token = generateToken(newUser);
    }

    private String generateToken(User user){
        return null;
    }
}
