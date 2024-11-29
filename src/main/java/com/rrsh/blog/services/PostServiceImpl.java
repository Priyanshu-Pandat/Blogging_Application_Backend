package com.rrsh.blog.services;

import com.rrsh.blog.entities.Category;
import com.rrsh.blog.entities.Post;
import com.rrsh.blog.entities.User;
import com.rrsh.blog.exceptions.ResourceNotFoundException;
import com.rrsh.blog.model.PostDto;

import com.rrsh.blog.model.PostResponse;
import com.rrsh.blog.reopsitories.CategoryRepo;
import com.rrsh.blog.reopsitories.PostRepo;
import com.rrsh.blog.reopsitories.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private UserRepo userRepo;
    @Override

    public PostDto ceratePost(PostDto postDto , Integer userId , Integer categoryId)
    {
        log.info("creating the post : {} " , " with " + postDto );

        User user = this.userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User" ,"id" , userId));
        Category category = this.categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category" ,"id" , categoryId));
        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = this.postRepo.save(post);
        log.info("created  the post : {} " , " with " + post );

        return this.modelMapper.map(savedPost , PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        log.info("Updating post : {}" , "with postId : " + postId);
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post" ,"id" , postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAddedDate(new Date());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);

        log.info("updated post : {}" , "with postId : " + postId);
        return this.modelMapper.map(updatedPost, PostDto.class);

    }

    @Override
    public void deletePost(Integer postDtoId) {
        log.info("deleting post by id : {}" , postDtoId);
        Post deletedPost = this.postRepo.findById(postDtoId)
                .orElseThrow(() -> new ResourceNotFoundException("postDto" ,"id" , postDtoId));
     this.postRepo.delete(deletedPost);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        log.info("getting post by id : {}" , postId);
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post" ,"id" , postId));

        return this.modelMapper.map(post , PostDto.class);
    }

    @Override
    public PostResponse getAll(Integer pageNumber , Integer pageSize , String sortBy, String sortDir) {
        log.info("getting all posts");
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
      Pageable p =  PageRequest.of(pageNumber, pageSize, sort );
        Page<Post> pagePost = this.postRepo.findAll(p);
      List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos =  posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        postResponse.setTotalElements(pagePost.getTotalElements());
           return postResponse ;
    }

    @Override
    public List<PostDto> getPosByCategory(Integer categoryId) {
        log.info("getting post by category : {}" , categoryId);

        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category" ,"id" , categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);
     List<PostDto> postDtos =    posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        log.info("posts are with post categoryId is {} :" , postDtos);
       System.out.println(postDtos);
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        log.info("getting post by user : {}" , userId);
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user" ,"id" , userId));
        List<Post>  posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos =    posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        log.info("posts are with post id is {} :" , postDtos);
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
     List<Post> posts = this.postRepo.findByTitleContaining(keyword);
     List<PostDto> postDtos =    posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
     return postDtos;
    }


}
