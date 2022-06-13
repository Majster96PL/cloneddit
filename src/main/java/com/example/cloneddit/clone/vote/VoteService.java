package com.example.cloneddit.clone.vote;

import com.example.cloneddit.api.ApiService;
import com.example.cloneddit.clone.post.Post;
import com.example.cloneddit.clone.post.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.cloneddit.clone.vote.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final ApiService apiService;

    @Transactional
    public void addVote(VoteDTO voteDTO){
        Post post = postRepository.findById(voteDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with ID -" + voteDTO.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository
                .findTopByPostAndUserOrderByVoteId(post, apiService.getEmailAsUser());
        if(voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDTO.getVoteType())){
            throw new RuntimeException(
                    "You have already " + voteDTO.getVoteType() + "for this post");
        }
        if(UPVOTE.equals(voteDTO.getVoteType())){
            post.setVoteNumber(post.getVoteNumber() + 1);
        }else{
            post.setVoteNumber(post.getVoteNumber() - 1);
        }
        voteRepository.save(mapToVote(voteDTO, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDTO voteDTO, Post post) {
        return Vote.builder()
                .voteType(voteDTO.getVoteType())
                .post(post)
                .user(apiService.getEmailAsUser())
                .build();

    }
}
