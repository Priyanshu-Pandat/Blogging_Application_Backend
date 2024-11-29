package com.rrsh.blog.entities;

import com.rrsh.blog.model.CommentDto;
import com.rrsh.blog.model.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @Column(name = "post_title")
    private String title;

    @Column(name = "post_content",length = 1000)

    private String content;

    @Column(name = "post_Imagename")
    private String imageName;

    @Column(name = "post_date")
    private Date addedDate;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private  Category category;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

}
