package com.example.socialnewsspring.repository;

import com.example.socialnewsspring.model.Comment;
import com.example.socialnewsspring.model.Post;
import com.example.socialnewsspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
