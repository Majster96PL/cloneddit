package com.example.cloneddit.clone;

import com.example.cloneddit.clone.post.Post;
import com.example.cloneddit.api.registration.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Cloneddit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long cloneId;
    @NotBlank(message = "Required Name!")
    private String name;
    @NotBlank(message = "Required Description")
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    private Instant createDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
