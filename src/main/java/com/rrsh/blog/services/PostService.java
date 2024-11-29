package com.rrsh.blog.services;

import com.rrsh.blog.model.PostDto;
import com.rrsh.blog.model.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    //create
    PostDto ceratePost(PostDto postDto , Integer userId , Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto , Integer postId);

    //delete
    void deletePost(Integer postDtoId);

    //getById
    PostDto getPostById(Integer postId);

    //getAll
    PostResponse getAll(Integer pageNumber , Integer pageSize , String SortBy , String  sortDir);

    //getByCategory  - get all posts of a specific category
    List<PostDto> getPosByCategory(Integer categoryId);

    //getByUser - get all posts of a specific user
    List<PostDto> getPostByUser(Integer userId) ;

    //search - search for posts by title or keyword
    List<PostDto> searchPosts(String keyword);

    // for file upload
   // String uploadFile(String path, MultipartFile file);

}
