package com.moonshade.week10secureblogapi.service;

import com.moonshade.week10secureblogapi.dto.requests.CommentRequest;
import com.moonshade.week10secureblogapi.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> findCommentsByPostId(Long id);

    CommentResponse saveComment(Long id, CommentRequest request);

    CommentResponse updateComment(Long postId, Long commentId, CommentRequest commentRequest);

    String deleteComment(Long postId, Long commentId, Long userId);
}
