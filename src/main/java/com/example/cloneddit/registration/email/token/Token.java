package com.example.cloneddit.registration.email.token;

import com.example.cloneddit.registration.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
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
