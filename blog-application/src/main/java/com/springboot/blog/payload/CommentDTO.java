package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private long id;

    //should not be null or empty
    @NotEmpty(message = "Name Should not be null or empty")
    private String name;

    //should not be null or empty
    //email field validation
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    //should not be null or empty
    //min 10 characters
    @NotEmpty
    @Size(min = 10, message = "Comments should be min 10 characters")
    private String body;
}
