package com.rrsh.blog.services;

import com.rrsh.blog.model.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto , Integer postId , Integer userId);
    void deleteComment(Integer commentId);
}
