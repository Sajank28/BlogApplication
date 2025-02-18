package com.springboot.blog.service;

import com.springboot.blog.payload.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createNewPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(long id);

    PostDTO editPostById(PostDTO postDTO, long id);

    void deletePostById(long id);
}
