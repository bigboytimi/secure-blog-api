package com.moonshade.week10secureblogapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentsInPostResponse {
    private String commenter;
    private String comment;
}
