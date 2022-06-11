package com.example.cloneddit.clone.mapper;

import com.example.cloneddit.api.registration.user.User;
import com.example.cloneddit.clone.comment.Comment;
import com.example.cloneddit.clone.comment.CommentDTO;
import com.example.cloneddit.clone.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "commentId" , ignore = true)
    @Mapping(target = "text", source = "commentDTO.text")
    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment map(CommentDTO commentDTO, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getFirstname())")
    CommentDTO mapOfCommentsToDTO(Comment comment);
}
