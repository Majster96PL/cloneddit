package com.example.cloneddit.clone.comment;

import com.example.cloneddit.api.ApiService;
import com.example.cloneddit.api.registration.user.User;
import com.example.cloneddit.api.registration.user.UserRepository;
import com.example.cloneddit.clone.mapper.CommentMapper;
import com.example.cloneddit.clone.post.Post;
import com.example.cloneddit.clone.post.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final ApiService apiService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void createComment(CommentDTO commentDTO){
        Post newPost = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(
                        () -> new RuntimeException(commentDTO.getPostId().toString()));
        Comment newComment = commentMapper.map(commentDTO, newPost, apiService.getEmailAsUser());
        commentRepository.save(newComment);

        //Może jak się zachce mi się to jakieś potwierdzenie na email, że ktoś coś skomentował zrobię
    }

    @Transactional
    public List<CommentDTO> getCommentsByPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new RuntimeException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapOfCommentsToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CommentDTO> getCommentsByUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapOfCommentsToDTO)
                .collect(Collectors.toList());
    }
}
