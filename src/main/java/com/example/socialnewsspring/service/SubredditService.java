package com.example.socialnewsspring.service;

import com.example.socialnewsspring.dto.SubredditDTO;
import com.example.socialnewsspring.exception.SubredditNotFoundException;
import com.example.socialnewsspring.model.Subreddit;
import com.example.socialnewsspring.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;

    @Transactional(readOnly = true)
    public List<SubredditDTO> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubredditDTO save(SubredditDTO subredditDTO) {
        // map SubredditDTO to Subreddit
        Subreddit subreddit = mapSubredditDTO(subredditDTO);
        // save
        Subreddit subredditSaved = subredditRepository.save(subreddit);

        // get Id for SubredditDTO
        subredditDTO.setId(subredditSaved.getId());

        return subredditDTO;
    }

    @Transactional(readOnly = true)
    public SubredditDTO getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id -" + id));
        return mapToDTO(subreddit);
    }

    private SubredditDTO mapToDTO(Subreddit subreddit) {
        return SubredditDTO.builder().id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .build();
    }

    private Subreddit mapSubredditDTO(SubredditDTO subredditDTO) {
        return Subreddit.builder().name(subredditDTO.getName())
                .description(subredditDTO.getDescription())
                .build();
    }
}
