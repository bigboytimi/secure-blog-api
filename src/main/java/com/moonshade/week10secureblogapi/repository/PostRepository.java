package com.moonshade.week10secureblogapi.repository;

import com.moonshade.week10secureblogapi.dto.requests.PostRequest;
import com.moonshade.week10secureblogapi.dto.response.PostResponse;
import com.moonshade.week10secureblogapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
