package com.example.cloneddit.api.registration.email.token;

import com.example.cloneddit.api.registration.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private LocalDateTime expiredDate;
    private LocalDateTime confirmedDate;
    private LocalDateTime createdDate;

    public Token(String token,
                 User user,
                 LocalDateTime expiredDate,
                 LocalDateTime createdDate) {
        this.token = token;
        this.user = user;
        this.expiredDate = expiredDate;
        this.createdDate = createdDate;
    }
}
