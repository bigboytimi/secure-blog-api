package com.moonshade.week10secureblogapi.service.implementation;

import com.moonshade.week10secureblogapi.entity.Comment;
import com.moonshade.week10secureblogapi.entity.Like;
import com.moonshade.week10secureblogapi.entity.Post;
import com.moonshade.week10secureblogapi.entity.UserEntity;
import com.moonshade.week10secureblogapi.exceptions.CommentNotFoundException;
import com.moonshade.week10secureblogapi.exceptions.PostNotFoundException;
import com.moonshade.week10secureblogapi.exceptions.UserNotFoundException;
import com.moonshade.week10secureblogapi.repository.CommentRepository;
import com.moonshade.week10secureblogapi.repository.LikeRepository;
import com.moonshade.week10secureblogapi.repository.PostRepository;
import com.moonshade.week10secureblogapi.repository.UserRepository;
import com.moonshade.week10secureblogapi.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    @Override
    public int getLikesByPostId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        List<Like> likes = likeRepository.findAllLikeByPostId(post.getId());
        return likes.size();
    }

    @Override
    public String likePost(Long postId, Long userId) {
        Like like = new Like();
        log.info("Checking if post with Id " + postId + " exists");
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));
        log.info("Checking if user with Id " + userId + " exists");
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        like.setPost(post);
        like.setUser(user);
        likeRepository.save(like);
        return "Post Liked Successfully!";
    }

    @Override
    public String likeComment(Long postId, Long commentId, Long userId) {
        Like like = new Like();
        log.info("Confirming if comment belongs to post");
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException(commentId));
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        if(comment.getPost().getId().equals(post.getId())){
            like.setUser(user);
            like.setPost(post);
            like.setComment(comment);
            likeRepository.save(like);
            return "Comment Liked Successfully";
        }

        return "Comment with Id " + commentId + " not found under post " + postId + "";
    }

    @Override
    public String unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));
        Like like = likeRepository.findLikeByPostId(post.getId());
        if(like != null) {
            likeRepository.delete(like);
            return "Success: Post unliked";
        } else {
            return "Failure: Cannot unlike this post";
        }
    }

    @Override
    public String unlikeComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException(commentId));
        if(comment.getPost().getId().equals(post.getId())){
            Like like = likeRepository.findLikeByCommentId(comment.getId());
            likeRepository.delete(like);
            return "Success: Comment Unliked";
        }
        return "Failure: Comment not found";
    }
}
