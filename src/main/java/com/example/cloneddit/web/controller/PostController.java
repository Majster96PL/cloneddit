package com.example.cloneddit.web.controller;

import com.example.cloneddit.clone.post.PostRequest;
import com.example.cloneddit.clone.post.PostResponse;
import com.example.cloneddit.clone.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(path = "/cloneddit/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.savePost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/by-clone/{cloneId}")
    public ResponseEntity<List<PostResponse>> getPostsByClone(@PathVariable Long cloneId){
        return status(HttpStatus.OK).body(postService.getPostByClone(cloneId));
    }

    @GetMapping("/by-user/{email}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable String email){
        return status(HttpStatus.OK).body(postService.getPostsByUser(email));
    }


}
