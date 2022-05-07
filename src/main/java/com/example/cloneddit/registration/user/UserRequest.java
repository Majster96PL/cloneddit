package com.example.cloneddit.registration.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String firstName;
    private String userName;
    private String email;
    private String password;
}
