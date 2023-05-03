package com.moonshade.week10secureblogapi.service;
public interface LikeService {
    int getLikesByPostId(Long id);

    String likePost(Long postId, Long userId);

    String likeComment(Long postId, Long commentId, Long userId);



    String unlikeComment(Long postId, Long commentId);

    String unlikePost(Long postId, Long userId);
}
