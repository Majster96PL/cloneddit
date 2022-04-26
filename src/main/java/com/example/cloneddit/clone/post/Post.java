package com.example.cloneddit.clone.post;

import com.example.cloneddit.clone.Cloneddit;
import com.example.cloneddit.registration.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postId;
    @NotBlank(message = "Post Name cannot be NULL!")
    private String postName;
    @Nullable
    private String urlPost;
    @Nullable
    @Lob
    private String description;
    private Integer voteNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cloneId", referencedColumnName = "cloneId")
    private Cloneddit cloneddit;
    private Instant createDate;

}
