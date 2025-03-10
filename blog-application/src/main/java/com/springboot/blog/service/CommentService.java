package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createNewComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(Long postId, Long commentId);

    CommentDTO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO);

    void deleteCommentById(Long postId, Long commentId);
}
