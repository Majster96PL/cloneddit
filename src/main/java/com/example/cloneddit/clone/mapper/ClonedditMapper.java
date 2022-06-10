package com.example.cloneddit.clone.mapper;

import com.example.cloneddit.clone.Cloneddit;
import com.example.cloneddit.clone.ClonedditDTO;
import com.example.cloneddit.clone.post.Post;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClonedditMapper {

    @Mapping(target = "postCount", expression = "java(postsMap(cloneddit.getPosts()))")
    ClonedditDTO mapCloneToDTO(Cloneddit cloneddit);

    default Integer postsMap(List<Post> posts){
        return posts.size();
    }

    @InheritConfiguration
    @Mapping(target = "posts", ignore = true)
    Cloneddit mapDTOToClone(ClonedditDTO clonedditDTO);
}
