package com.springboot.blog.service;

import com.springboot.blog.payload.PostDTO;

public interface PostService {

    PostDTO createNewPost(PostDTO postDTO);
}
