package com.example.socialnewsspring.service;

import com.example.socialnewsspring.dto.VoteDTO;
import com.example.socialnewsspring.exception.PostNotFoundException;
import com.example.socialnewsspring.exception.SpringRedditException;
import com.example.socialnewsspring.model.Post;
import com.example.socialnewsspring.model.Vote;
import com.example.socialnewsspring.model.VoteType;
import com.example.socialnewsspring.repository.PostRepository;
import com.example.socialnewsspring.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDTO voteDTO) {
        Post post = postRepository.findById(voteDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDTO.getPostId()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDTO.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDTO.getVoteType() + "'d for this post");
        }
        if (VoteType.UPVOTE.equals(voteDTO.getVoteType()) && !voteByPostAndUser.isPresent()) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else if (VoteType.DOWNVOTE.equals(voteDTO.getVoteType()) && !voteByPostAndUser.isPresent()) {
            post.setVoteCount(post.getVoteCount() - 1);
        } else if (VoteType.UPVOTE.equals(voteDTO.getVoteType()) && voteByPostAndUser.isPresent()) {
            post.setVoteCount(post.getVoteCount() + 2);
        } else if (VoteType.DOWNVOTE.equals(voteDTO.getVoteType()) && voteByPostAndUser.isPresent()) {
            post.setVoteCount(post.getVoteCount() - 2);
        }

        voteRepository.save(mapToVote(voteDTO, post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDTO voteDTO, Post post) {
        return Vote.builder()
                .voteType(voteDTO.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
