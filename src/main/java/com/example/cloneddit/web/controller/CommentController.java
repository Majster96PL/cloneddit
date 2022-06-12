package com.example.cloneddit.web.controller;

import com.example.cloneddit.clone.comment.CommentDTO;
import com.example.cloneddit.clone.comment.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/cloneddit/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDTO commentDTO){
        commentService.createComment(commentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId){
        return status(HttpStatus.OK).body(commentService.getCommentsByPost(postId));
    }

    @GetMapping("/by-user/{user}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByUser(@PathVariable String user){
        return status(HttpStatus.OK).body(commentService.getCommentsByUser(user));
    }
}
