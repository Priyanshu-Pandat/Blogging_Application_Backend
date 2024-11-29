package com.rrsh.blog.controllers;

import com.rrsh.blog.model.Apiresponce;
import com.rrsh.blog.model.UserDto;
import com.rrsh.blog.services.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping ("/add")
    public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new  ResponseEntity<>(createUserDto , HttpStatus.CREATED);
    }
    @PutMapping("update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable int userId) {
        UserDto updateUserDto = this.userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUser() {
       // List<UserDto> userDtoList = this.userService.getAllUsers();
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Apiresponce> deleteUser(@PathVariable Integer userId) {
      this.userService.deleteUser(userId);
      return new  ResponseEntity(new Apiresponce("User Deleted Successfolly" , true) , HttpStatus.OK);

    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
        // List<UserDto> userDtoList = this.userService.getAllUsers();
        return new ResponseEntity<>(this.userService.getUserById(userId), HttpStatus.OK);
    }
}

