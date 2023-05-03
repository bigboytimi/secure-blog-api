package com.moonshade.week10secureblogapi.service.implementation;

import com.moonshade.week10secureblogapi.dto.requests.CommentRequest;
import com.moonshade.week10secureblogapi.dto.response.CommentResponse;
import com.moonshade.week10secureblogapi.entity.Comment;
import com.moonshade.week10secureblogapi.entity.Post;
import com.moonshade.week10secureblogapi.entity.UserEntity;
import com.moonshade.week10secureblogapi.exceptions.CommentNotFoundException;
import com.moonshade.week10secureblogapi.exceptions.PostNotFoundException;
import com.moonshade.week10secureblogapi.exceptions.UserNotFoundException;
import com.moonshade.week10secureblogapi.repository.CommentRepository;
import com.moonshade.week10secureblogapi.repository.PostRepository;
import com.moonshade.week10secureblogapi.repository.UserRepository;
import com.moonshade.week10secureblogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponse mapToResponse(Comment comment){
        return CommentResponse.builder()
                .postId(comment.getPost().getId())
                .postTitle(comment.getPost().getTitle())
                .comment(comment.getComment())
                .commenter(comment.getUser().getUsername())
                .build();
    }
    @Override
    public List<CommentResponse> findCommentsByPostId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        List<Comment> comments = commentRepository.findAllByPostId(post.getId());
        return comments.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public CommentResponse saveComment(Long id, CommentRequest request) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(()-> new UserNotFoundException(request.getUserId()));
        Comment comment = Comment.builder()
                .comment(request.getComment())
                .post(post)
                .user(user)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return mapToResponse(savedComment);
    }

    @Override
    public CommentResponse updateComment(Long postId, Long commentId, CommentRequest commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException(commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new CommentNotFoundException(comment.getId());
        } else if (!comment.getUser().getId().equals(commentRequest.getUserId())){
            throw new UserNotFoundException(commentRequest.getUserId());
        }
        comment.setComment(commentRequest.getComment());
        comment.setPost(post);
        Comment updatedComment = commentRepository.save(comment);
        return mapToResponse(updatedComment);
    }

    @Override
    public String deleteComment(Long postId, Long commentId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException(commentId));
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));

        if(!comment.getPost().getId().equals(post.getId())){
            return "Comment does not belong to post";
        } else if(!comment.getUser().getId().equals(user.getId())){
            return "User is not the poster of comment";
        }
        commentRepository.delete(comment);
        return "Comment deleted";
    }
}
