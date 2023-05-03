package com.moonshade.week10secureblogapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {
    private Long postId;
    private String title;
    private int likes;
}
