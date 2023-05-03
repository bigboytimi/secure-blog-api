package com.moonshade.week10secureblogapi.controller;

import com.moonshade.week10secureblogapi.dto.requests.CommentRequest;
import com.moonshade.week10secureblogapi.dto.requests.LikeRequest;
import com.moonshade.week10secureblogapi.dto.response.CommentResponse;
import com.moonshade.week10secureblogapi.repository.PostRepository;
import com.moonshade.week10secureblogapi.service.CommentService;
import com.moonshade.week10secureblogapi.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {
    //users can comment,
    // get all comments, (& admin)
    // update or remove comment, (& admin)
    // like posts and comments($ admin)
    // delete comments ( & admin)

    private final CommentService commentService;
    private final LikeService likeService;
    private final PostRepository postRepository;

    @PostMapping("{postId}/comments/comment")
    public ResponseEntity<CommentResponse> createComment(@PathVariable("postId")Long id, @Valid @RequestBody CommentRequest request){
        log.info("Saving a comment {}", request);
        CommentResponse comment = commentService.saveComment(id, request);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("postId") Long postId,
                                                         @PathVariable("commentId") Long commentId,
                                                         @Valid @RequestBody CommentRequest commentRequest){
        log.info("Updating comment {}", commentRequest);
        CommentResponse comment = commentService.updateComment(postId, commentId, commentRequest);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comments/{commentId}/{userId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId,
                                                @PathVariable("commentId") Long commentId,
                                                @PathVariable("userId") Long userId){
        log.info("Deleting comment with Id: " + commentId);
        String status = commentService.deleteComment(postId, commentId, userId);
        return  new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable("postId") Long postId, @Valid @RequestBody LikeRequest request){
        String message = likeService.likePost(postId, request.getUserId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable("postId") Long postId, @Valid @RequestBody LikeRequest request){
        String message = likeService.unlikePost(postId, request.getUserId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
                                              @Valid @RequestBody LikeRequest request){
        String message = likeService.likeComment(postId, commentId, request.getUserId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comments/{commentId}/unlike")
    public ResponseEntity<String> unlikeComment(@PathVariable("postId") Long postId, @PathVariable ("commentId") Long commentId){
        String message = likeService.unlikeComment(postId, commentId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
