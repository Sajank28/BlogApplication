package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

//this is a java bean
@Data
public class PostDTO {

    private Long id;

    //title should not be null or empty
    //title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post Title should have at least 2 characters")
    private String title;

    //Post Desc should not be null or empty
    // at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "post description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDTO> comments;

    private Long categoryId;
}
