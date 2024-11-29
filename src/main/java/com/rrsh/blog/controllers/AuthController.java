package com.rrsh.blog.controllers;

import com.rrsh.blog.model.JwtAuthRequest;
import com.rrsh.blog.model.JwtAuthResponse;
import com.rrsh.blog.security.JWT_TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {
    @Autowired
    private JWT_TokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
     this.authenticate(request.getUsername(), request.getPassword());
     UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
    String token  =  this.jwtTokenHelper.generateToken(userDetails);
    JwtAuthResponse response = new JwtAuthResponse();
           response.setToken(token);
    return new ResponseEntity<JwtAuthResponse>(response , HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
            System.out.println("Authentication successful for user: " + username);
        } catch (Exception e) {
            System.out.println("Authentication failed for user: " + username + " - Error: " + e.getMessage());
            throw e; // Rethrow the exception to return 401
        }
    }


}
