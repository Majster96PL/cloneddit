package com.example.cloneddit.clone.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String clonedditName;
    private Integer voteCount;
    private Integer commentVote;
    private String duration;

}
