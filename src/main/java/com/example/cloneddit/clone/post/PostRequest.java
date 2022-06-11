package com.example.cloneddit.clone.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    private String clonedditName;
    private String postName;
    private String url;
    private String description;
}
