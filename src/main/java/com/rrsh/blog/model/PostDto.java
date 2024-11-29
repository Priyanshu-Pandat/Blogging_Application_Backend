package com.rrsh.blog.model;

import com.rrsh.blog.entities.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private int PostId;
    @NotNull(message = "required")
    private String title;
    @NotEmpty(message = "content not empty")
    private String content;
    private Date addedDate;
    @NotNull(message = "user not null")
    private UserDto user;
    @NotNull( message = "image not null")
    private String imageName;
    @NotNull(message = "category not null")
    private CategoryDto category;
    private List<CommentDto> comments = new LinkedList<>();


}
