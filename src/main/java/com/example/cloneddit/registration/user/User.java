package com.example.cloneddit.registration.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @NotBlank(message = "Required Username!")
    private String firstName;
    @NotBlank(message = "Required Password!")
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private boolean enabledUser;
    private boolean lockedUser;

    public User(String firstName,
                String password,
                String email,
                UserRole role) {
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !lockedUser;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabledUser;
    }
}
 