package com.springboot.blog.service;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createNewPost(PostDTO postDTO);

//    List<PostDTO> getAllPosts();

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(long id);

    PostDTO editPostById(PostDTO postDTO, long id);

    void deletePostById(long id);

    List<PostDTO> getPostByCategory(long categoryId);
}
