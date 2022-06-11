package com.example.cloneddit.clone.mapper;

import com.example.cloneddit.api.registration.user.User;
import com.example.cloneddit.clone.Cloneddit;
import com.example.cloneddit.clone.post.Post;
import com.example.cloneddit.clone.post.PostRequest;
import com.example.cloneddit.clone.post.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "cloneddit", source = "cloneddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Cloneddit cloneddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "urlPost")
    @Mapping(target = "clonedditName", source = "cloneddit.name")
    @Mapping(target = "userName", source = "user.firstname")
    PostResponse mapOfPostToDTO(Post post);
}
