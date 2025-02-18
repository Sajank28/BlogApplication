package com.springboot.blog.service.impl;

import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {
        // DTO to JPA
        Post post = modelMapper.map(postDTO, Post.class);

        //save to Repo
        Post savedPost = postRepository.save(post);

        //return Dto (Saved JPA to DTO)

        return modelMapper.map(savedPost, PostDTO.class );
    }
}
