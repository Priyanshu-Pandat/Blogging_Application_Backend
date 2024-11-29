package com.rrsh.blog.controllers;

import com.rrsh.blog.entities.Comment;
import com.rrsh.blog.exceptions.ResourceNotFoundException;
import com.rrsh.blog.model.Apiresponce;
import com.rrsh.blog.model.CommentDto;
import com.rrsh.blog.reopsitories.CommentRepo;
import com.rrsh.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepo commentRepo;

    @PostMapping("postId/{postId}/userId/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Integer postId,
                                                     @PathVariable Integer userId ) {
        CommentDto createdComment = this.commentService.createComment(commentDto, postId, userId);
        return new  ResponseEntity<CommentDto>(createdComment ,HttpStatus.CREATED);
    }
    @DeleteMapping("deleteComment/{commentId}")
    public ResponseEntity<Apiresponce> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<Apiresponce>
                (new Apiresponce("Comment deleted successfully" , true), HttpStatus.OK);
    }



}
