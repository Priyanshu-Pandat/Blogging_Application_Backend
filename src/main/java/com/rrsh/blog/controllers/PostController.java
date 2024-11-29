package com.rrsh.blog.controllers;


import com.rrsh.blog.model.Apiresponce;
import com.rrsh.blog.model.AppConstant;
import com.rrsh.blog.model.PostDto;
import com.rrsh.blog.model.PostResponse;
import com.rrsh.blog.services.FileService;
import com.rrsh.blog.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@RestController
@Log4j2
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    // Create post
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@Valid  @RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {
        PostDto createdPost = this.postService.ceratePost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @PutMapping("update/{postId}")
    public ResponseEntity<PostDto> updatePost( @Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer postId)
    {
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }
    @DeleteMapping("delete/{postId}")
    public ResponseEntity<Apiresponce> deletePost(@PathVariable Integer postId) {
         this.postService.deletePost(postId);
         return  new ResponseEntity<Apiresponce>( new Apiresponce(" post deleted successfully" , true), HttpStatus.OK);
    }
    // get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId ) {
        List<PostDto> posts = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts , HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId ) {
        List<PostDto> posts = this.postService.getPosByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts , HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber ,
            @RequestParam (value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam (value = "sortBy"  , defaultValue = AppConstant.SORT_BY, required = false) String  sortBy,
            @RequestParam (value = "sortDir" , defaultValue = AppConstant.SORT_DIR , required = false) String sortDir){
        PostResponse postResponse = this.postService.getAll(pageNumber , pageSize , sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }
    //get post by postId
    @GetMapping("/getByPostId/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }
    // search posts by keyword

   @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable  String keyword) {
    List<PostDto> posts = this.postService.searchPosts(keyword);
    return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

   }
   // method for upload the image
   @PostMapping("/image/upload/{postId}")
   public  ResponseEntity<PostDto> uploadPostImage( @Valid
           @RequestParam("image")MultipartFile image
           , @PathVariable Integer postId) throws IOException {

       PostDto post = this.postService.getPostById(postId);
       String fileName =  this.fileService.uploadImage(path, image);

       post.setImageName(fileName);
       PostDto updatePost = this.postService.updatePost(post, postId);
      log.info("the image name is: " + post.getImageName());
       return new ResponseEntity<PostDto>(updatePost , HttpStatus.OK);
   }

    // method for serve the image
    @GetMapping(value = "/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName ,
                              HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path , imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("the path is  " + imageName);
        StreamUtils.copy(resource , response.getOutputStream());
    }


}

