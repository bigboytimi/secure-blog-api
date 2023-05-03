package com.moonshade.week10secureblogapi.dto.response;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String postedBy;
    private String posterId;
    private List<CommentsInPostResponse> comments;
    private int likes;
}
