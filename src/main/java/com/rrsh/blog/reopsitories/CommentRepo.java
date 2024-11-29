package com.rrsh.blog.reopsitories;

import com.rrsh.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
