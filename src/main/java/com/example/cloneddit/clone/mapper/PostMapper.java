package com.example.cloneddit.clone.mapper;

import com.example.cloneddit.api.ApiService;
import com.example.cloneddit.api.registration.user.User;
import com.example.cloneddit.clone.Cloneddit;
import com.example.cloneddit.clone.comment.CommentRepository;
import com.example.cloneddit.clone.post.Post;
import com.example.cloneddit.clone.post.PostRequest;
import com.example.cloneddit.clone.post.PostResponse;
import com.example.cloneddit.clone.vote.Vote;
import com.example.cloneddit.clone.vote.VoteRepository;
import com.example.cloneddit.clone.vote.VoteType;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.cloneddit.clone.vote.VoteType.DOWNVOTE;
import static com.example.cloneddit.clone.vote.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private ApiService apiService;
    @Autowired
    private VoteRepository voteRepository;

    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "cloneddit", source = "cloneddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "urlPost", source = "postRequest.url")
    @Mapping(target = "voteNumber", constant = "0")
    public abstract Post map(PostRequest postRequest, Cloneddit cloneddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "urlPost")
    @Mapping(target = "clonedditName", source = "cloneddit.name")
    @Mapping(target = "userName", source = "user.firstname")
    @Mapping(target = "commentVote", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapOfPostToDTO(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreateDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (apiService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteId(post,
                            apiService.getEmailAsUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
