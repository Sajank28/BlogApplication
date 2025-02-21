package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
@Tag(
        name = "CRUD REST APIs for POST RESOURCE"
)
public class PostController {

    private PostService postService;

    //@RequestBody -convert json into java obj
    @Operation(
            summary = "Create POST REST API",
            description = "Create POST REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "BasicAuth"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        PostDTO newPost = postService.createNewPost(postDTO);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<List<PostDTO>> getAllPosts(){
//        return new ResponseEntity<>(postService.getAllPosts(),HttpStatus.OK);
//    }


    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    //required=false means optional query
    @Operation(
            summary = "GET POST REST API",
            description = "GET POST REST API is used to retrieve all Posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_By, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        PostResponse getAllPost = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllPost,HttpStatus.OK);
    }

    @Operation(
            summary = "GET POST BY ID POST REST API",
            description = "GET POST REST API is used to retrieve Post from database by POSTID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        PostDTO postDTO = postService.getPostById(id);
        return ResponseEntity.ok(postDTO);
    }

    @Operation(
            summary = "UPDATE POST REST API",
            description = "GET POST REST API is used to UPDATE Post with id and save in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "BasicAuth"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@PathVariable long id,
                                              @Valid @RequestBody PostDTO postDTO){
        return ResponseEntity.ok(postService.editPostById(postDTO, id));
    }

    @Operation(
            summary = "DELETE POST REST API",
            description = "DELETE POST REST API is used to DELETE Post from database using ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "BasicAuth"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post Deleted Successfully!");
    }

    @Operation(
            summary = "GET POST BY CATEGORY ID REST API",
            description = "GET POST BY CATEGORY ID REST API is used to retrieve Post by Categroy ID from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDTO>> getPostByCategoryId(@PathVariable("id") long categoryId){
        List<PostDTO> postDTOS = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postDTOS);
    }
}
