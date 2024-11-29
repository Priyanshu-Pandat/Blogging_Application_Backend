package com.rrsh.blog.services;

import com.rrsh.blog.entities.User;
import com.rrsh.blog.exceptions.ResourceNotFoundException;
import com.rrsh.blog.model.UserDto;
import com.rrsh.blog.reopsitories.UserRepo;
import lombok.extern.log4j.Log4j2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepo userRepo;
   @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("create  the user : {} " , " with " + userDto );
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }



    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        log.info("update the user : {} " , "with userId " + userId );
        User user = this.userRepo.findById(userId).orElseThrow( ()-> new ResourceNotFoundException(
                "User","id", userId
        ));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        User updatedUser = this.userRepo.save(user);
        UserDto updatedDto1 = this.userToDto(updatedUser);
        return updatedDto1;
    }

    @Override
    public UserDto getUserById(Integer id) {
        log.info("get the user : {} " , "with userId " + id );
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user" ,"Id" , id));
        return  this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.info("getting all the user " );
        List<User> users = this.userRepo.findAll();
       List<UserDto> userDtos =  users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
       return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        log.info("delete the user : {} " , "with userId " + userId );
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user" ,"Id" , userId));
      this.userRepo.delete(user);
    }
    public User dtoToUser(UserDto userDto) {
       User user= this.modelMapper.map(userDto,User.class);
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
       return user;
    }

    public UserDto userToDto(User user) {
     UserDto userDto = this.modelMapper.map(user,UserDto.class);
//        UserDto userDto = new UserDto();
//       userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
