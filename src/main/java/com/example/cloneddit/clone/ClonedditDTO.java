package com.example.cloneddit.clone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClonedditDTO {
    private Long id;
    private String name;
    private String description;
    private Integer postCount;
}
