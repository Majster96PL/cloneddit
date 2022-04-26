package com.example.cloneddit.registration.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @NotBlank(message = "Required Username!")
    private String username;
    @NotBlank(message = "Required Password!")
    private String password;
    @Email
    @NotBlank(message = "Required Email!")
    private String email;
    private Instant createdUser;
    private boolean enabledUser;

}
 