package com.example.socialnewsspring.mapper;

import com.example.socialnewsspring.dto.CommentsDTO;
import com.example.socialnewsspring.model.Comment;
import com.example.socialnewsspring.model.Post;
import com.example.socialnewsspring.model.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDTO.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public abstract Comment map(CommentsDTO commentsDTO, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "duration", expression = "java(getDuration(comment))")
    public abstract CommentsDTO mapToDTO(Comment comment);

    String getDuration(Comment comment) {
        return TimeAgo.using(comment.getCreatedDate().toEpochMilli());
    }
}
