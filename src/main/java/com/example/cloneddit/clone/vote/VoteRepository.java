package com.example.cloneddit.clone.vote;

import com.example.cloneddit.clone.post.Post;
import com.example.cloneddit.registration.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteId(Post post, User user);
}
