package com.example.cloneddit.clone.comment;

import com.example.cloneddit.clone.post.Post;
import com.example.cloneddit.registration.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long > {
    List<Comment>findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
