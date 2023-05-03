package com.moonshade.week10secureblogapi.dto.requests;

import com.moonshade.week10secureblogapi.dto.response.CommentResponse;
import lombok.*;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String comment;
    private Long userId;

}
