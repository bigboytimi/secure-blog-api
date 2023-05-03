package com.moonshade.week10secureblogapi.controller;

import com.moonshade.week10secureblogapi.dto.requests.PostRequest;
import com.moonshade.week10secureblogapi.dto.response.CommentResponse;
import com.moonshade.week10secureblogapi.dto.response.LikeResponse;
import com.moonshade.week10secureblogapi.dto.response.PostResponse;
import com.moonshade.week10secureblogapi.dto.response.UserResponse;
import com.moonshade.week10secureblogapi.entity.Like;
import com.moonshade.week10secureblogapi.entity.Post;
import com.moonshade.week10secureblogapi.exceptions.PostNotFoundException;
import com.moonshade.week10secureblogapi.repository.PostRepository;
import com.moonshade.week10secureblogapi.service.CommentService;
import com.moonshade.week10secureblogapi.service.LikeService;
import com.moonshade.week10secureblogapi.service.PostService;
import com.moonshade.week10secureblogapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/")
public class AdminController {
    //can post delete post, get comments
    //view all comments and likes
    //view all users
    //delete user
    //have access to everything users have access to
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final PostRepository postRepository;
    @PreAuthorize("hasRole('Role_ADMIN')")
    @PostMapping("/posts/create-post")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request){
        PostResponse response = postService.savePost(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('Role_ADMIN')")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long id){
        String message = postService.deletePost(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getSinglePost(@PathVariable("postId") Long id){
        PostResponse post = postService.findPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Role_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        List<PostResponse> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Role_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable("postId") Long id){
        List<CommentResponse> response = commentService.findCommentsByPostId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Role_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/{postId}/likes")
    public ResponseEntity<LikeResponse> getLikes(@PathVariable("postId") Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        if(post != null){
            int likeCount = likeService.getLikesByPostId(post.getId());
            LikeResponse response = LikeResponse.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .likes(likeCount)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('Role.ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Role.ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long id){
         String status = userService.deleteUserByPostId(id);
         return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
