package com.example.socialnewsspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubredditDTO {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
