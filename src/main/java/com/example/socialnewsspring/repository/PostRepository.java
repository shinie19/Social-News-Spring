package com.example.socialnewsspring.repository;

import com.example.socialnewsspring.model.Post;
import com.example.socialnewsspring.model.Subreddit;
import com.example.socialnewsspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
