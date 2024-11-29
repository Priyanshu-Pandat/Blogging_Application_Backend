package com.rrsh.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWT_TokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Skip filtering for specific endpoints
        if (requestURI.startsWith("/api/v1/auth/login/")) {
            System.out.println("Skipping JWT validation for: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // Existing JWT validation logic
        String requestToken = request.getHeader("Authorization");
        String userName = null;
        String token = null;

        if (requestToken != null && !requestToken.isEmpty() && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
            try {
                userName = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token expired and not valid");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is expired");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            }
        } else {
            System.out.println("JWT token does not begin with Bearer or is empty");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (userDetails != null && this.jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                System.out.println("User details not found or token is not valid");
            }
        } else {
            System.out.println("Username is null or context is not null");
        }

        filterChain.doFilter(request, response);
    }

}
