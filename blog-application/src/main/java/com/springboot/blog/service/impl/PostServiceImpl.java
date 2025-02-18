package com.springboot.blog.service.impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {
        // convert DTO to JPA
        Post post = modelMapper.map(postDTO, Post.class);

        //save JPA to Repo
        Post newPost = postRepository.save(post);

        //return Dto (convert newPost JPA to DTO)
        return modelMapper.map(newPost, PostDTO.class );
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> post = postRepository.findAll();

        //convert List<Post> to List<PostDTO>
        return post.stream().map((posts) -> modelMapper.map(posts, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        //convert Post entity  to PostDTO
        return modelMapper.map(post,PostDTO.class);
    }

    @Override
    public PostDTO editPostById(PostDTO postDTO, long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        //set all updates to Post Entity
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(post);

        //convert JPA to Dto
        return modelMapper.map(updatedPost,PostDTO.class);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }
}
