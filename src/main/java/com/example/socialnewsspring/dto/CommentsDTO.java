package com.example.socialnewsspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {
    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;
    private String duration;
}