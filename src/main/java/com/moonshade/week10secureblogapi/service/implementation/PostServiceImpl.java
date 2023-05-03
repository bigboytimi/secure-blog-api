package com.moonshade.week10secureblogapi.service.implementation;

import com.moonshade.week10secureblogapi.dto.requests.PostRequest;
import com.moonshade.week10secureblogapi.dto.response.PostResponse;
import com.moonshade.week10secureblogapi.entity.Post;
import com.moonshade.week10secureblogapi.exceptions.PostNotFoundException;
import com.moonshade.week10secureblogapi.repository.PostRepository;
import com.moonshade.week10secureblogapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;
    @Override
    public PostResponse savePost(PostRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        Post post = modelMapper.map(request, Post.class);
        Post updatedPost = postRepository.save(post);
        return mapToResponse(updatedPost);
    }

    @Override
    public String deletePost(Long id) {
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
            return "Post with ID " + " deleted";
        }
        return "Post not found";
    }

    @Override
    public PostResponse findPostById(Long id) {
       Post existingPost = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException(id));
       return mapToResponse(existingPost);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PostResponse mapToResponse(Post post){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(post, PostResponse.class);
    }
}
