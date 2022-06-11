package com.example.cloneddit.clone.post;

import com.example.cloneddit.api.ApiService;
import com.example.cloneddit.api.registration.user.User;
import com.example.cloneddit.api.registration.user.UserRepository;
import com.example.cloneddit.clone.Cloneddit;
import com.example.cloneddit.clone.ClonedditRepository;
import com.example.cloneddit.clone.mapper.PostMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClonedditRepository clonedditRepository;
    private final PostMapper postMapper;
    private final ApiService apiService;

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post newPost = postRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(id.toString())
                );
        return postMapper.mapOfPostToDTO(newPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapOfPostToDTO)
                .collect(toList());
    }

    public void savePost(PostRequest postRequest){
        Cloneddit newClone = clonedditRepository.findByName(
                postRequest.getClonedditName())
                .orElseThrow(
                        () -> new RuntimeException(postRequest.getClonedditName()));
        postRepository.save(postMapper.map(postRequest, newClone, apiService.getEmailAsUser()));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostByClone(Long id){
        Cloneddit clone = clonedditRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(id.toString()));
        List<Post> posts = postRepository.findAllByCloneddit(clone);
        return posts.stream().map(postMapper::mapOfPostToDTO).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUser(String email){
        User newUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(email));
        return postRepository.findByUser(newUser)
                .stream()
                .map(postMapper::mapOfPostToDTO)
                .collect(toList());
    }

}
