package com.moonshade.week10secureblogapi.repository;

import com.moonshade.week10secureblogapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value = "SELECT * FROM likes WHERE post_id = ?1", nativeQuery = true)
    List<Like> findAllLikeByPostId(Long id);

    @Query(value = "SELECT * FROM likes WHERE post_id = ?1", nativeQuery = true)
    Like findLikeByPostId(Long id);

    @Query(value = "SELECT * FROM likes WHERE comment_Id = ?1", nativeQuery = true)
    Like findLikeByCommentId(Long id);
}
