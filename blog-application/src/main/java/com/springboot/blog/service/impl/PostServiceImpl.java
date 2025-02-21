package com.springboot.blog.service.impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Category;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","id", postDTO.getCategoryId()));
        // convert DTO to JPA
        Post post = modelMapper.map(postDTO, Post.class);
        post.setCategory(category);

        //save JPA to Repo
        Post newPost = postRepository.save(post);

        //return Dto (convert newPost JPA to DTO)
        return modelMapper.map(newPost, PostDTO.class );
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageable instance
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
        //create Pageable instance and add Sort method pass string as properties
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                                                                    //Sort.by(sortBy).descending

        Page<Post> post = postRepository.findAll(pageable);

        // get content from page object
        List<Post> listOfPosts = post.getContent();

        //convert List<Post> to List<PostDTO>
        List<PostDTO> content = listOfPosts.stream().map((posts) -> modelMapper.map(posts, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(post.getNumber());
        postResponse.setPageSize(post.getSize());
        postResponse.setTotalElements(post.getTotalElements());
        postResponse.setTotalPages(post.getTotalPages());
        postResponse.setLast(post.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        //convert Post entity  to PostDTO
        return modelMapper.map(post,PostDTO.class);
    }

    @Override
    public PostDTO editPostById(PostDTO postDTO, long id) {

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","id", postDTO.getCategoryId()));

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        //set all updates to Post Entity
        post.setCategory(category);
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

    @Override
    public List<PostDTO> getPostByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post)-> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }
}
