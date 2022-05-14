package com.example.socialnewsspring.service;

import com.example.socialnewsspring.dto.CommentsDTO;
import com.example.socialnewsspring.exception.PostNotFoundException;
import com.example.socialnewsspring.mapper.CommentMapper;
import com.example.socialnewsspring.model.Comment;
import com.example.socialnewsspring.model.NotificationEmail;
import com.example.socialnewsspring.model.Post;
import com.example.socialnewsspring.model.User;
import com.example.socialnewsspring.repository.CommentRepository;
import com.example.socialnewsspring.repository.PostRepository;
import com.example.socialnewsspring.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    //TODO: Construct POST URL
    private static final String POST_URL = "";

    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void createComment(CommentsDTO commentsDTO) {
        Post post = postRepository.findById(commentsDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDTO.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDTO, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, authService.getCurrentUser());
    }

    public List<CommentsDTO> getCommentByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CommentsDTO> getCommentsByUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }
}