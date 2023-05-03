package com.moonshade.week10secureblogapi.service;

import com.moonshade.week10secureblogapi.dto.requests.PostRequest;
import com.moonshade.week10secureblogapi.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse savePost(PostRequest request);

    String deletePost(Long id);

    PostResponse findPostById(Long id);

    List<PostResponse> getAllPosts();
}
