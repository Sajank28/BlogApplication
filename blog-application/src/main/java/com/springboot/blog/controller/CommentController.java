package com.springboot.blog.controller;

import com.springboot.blog.model.Comment;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createNewComment(@PathVariable("postId") long postId,
                                                       @Valid @RequestBody CommentDTO commentDTO){
        CommentDTO newComment = commentService.createNewComment(postId, commentDTO);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable("postId") long postId){
        List<CommentDTO> commentDTO = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentsById(@PathVariable("postId") long postId,
                                                      @PathVariable("id") long commentId){
        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateCommentById(@PathVariable("postId") long postId,
                                        @PathVariable("id") long commentId,
                                        @Valid @RequestBody CommentDTO commentDTO){
        CommentDTO updatedComment = commentService.updateCommentById( postId, commentId, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") long postId,
                                                    @PathVariable(value = "id") long commentId){
        commentService.deleteCommentById(postId, commentId);

        return ResponseEntity.ok("Comment Deleted Successfully!");
    }
}
