package com.example.socialnewsspring.controller;

import com.example.socialnewsspring.dto.CommentsDTO;
import com.example.socialnewsspring.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDTO commentsDTO) {
        commentService.createComment(commentsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post")
    public ResponseEntity<List<CommentsDTO>> getAllCommentsForPost(@RequestParam("postId") Long postId) {
        return status(HttpStatus.OK)
                .body(commentService.getCommentByPost(postId));
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<CommentsDTO>> getAllCommentsByUser(@RequestParam("userName") String userName) {
        return status(HttpStatus.OK).body(commentService.getCommentsByUser(userName));
    }
}
