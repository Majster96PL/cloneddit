package com.example.cloneddit.clone.post;

import com.example.cloneddit.clone.Cloneddit;
import com.example.cloneddit.api.registration.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCloneddit(Cloneddit cloneddit);
    List<Post> findByUser(User user);
}
