package com.example.blog_platform.security.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = jwtUtils.getJwtFromHeader(request);

            if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
                String userName = jwtUtils.getUsernameFromToken(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                System.out.println(userName);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(
                        "Authorities for " + userDetails.getUsername() + ": " +
                                userDetails.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList()
                );

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Important: continue with the chain
        filterChain.doFilter(request, response);
    }
}
