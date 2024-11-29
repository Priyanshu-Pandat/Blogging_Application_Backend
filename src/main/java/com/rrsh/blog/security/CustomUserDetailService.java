package com.rrsh.blog.security;

import com.rrsh.blog.entities.User;
import com.rrsh.blog.exceptions.ResourceNotFoundException;
import com.rrsh.blog.reopsitories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Log the username
        System.out.println("Loading user by username: " + username);

        // Attempt to find the user
        User user = this.userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userEmail"+ username, 0 ));

        // Log if the user is found
        System.out.println("User found: " + user.getEmail());

        return user;
    }

}
