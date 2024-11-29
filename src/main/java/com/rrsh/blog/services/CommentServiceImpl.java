package com.rrsh.blog.services;

import com.rrsh.blog.entities.Comment;
import com.rrsh.blog.entities.Post;
import com.rrsh.blog.entities.User;
import com.rrsh.blog.exceptions.ResourceNotFoundException;
import com.rrsh.blog.model.CommentDto;
import com.rrsh.blog.reopsitories.CommentRepo;
import com.rrsh.blog.reopsitories.PostRepo;
import com.rrsh.blog.reopsitories.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        log.info("Creating the comment with postId: {}", postId );
        log.info("Creating the comment with userId : {}", userId );
        User user = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("user" ,"id" , userId));
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post" ,"id" , postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = this.commentRepo.save(comment);
        log.info("Created comment with id : {} ", savedComment.getCommentId());
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        log.info("deleting the comment " + commentId);
    Comment com = this.commentRepo.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("comment" ,"id" , commentId));
    log.info("Comment deleted successfully with id : {} ", commentId);
    this.commentRepo.delete(com);
    }
}
