package com.moonshade.week10secureblogapi.dto.response;

import com.moonshade.week10secureblogapi.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long postId;
    private String postTitle;
    private String commenter;
    private String comment;
}
