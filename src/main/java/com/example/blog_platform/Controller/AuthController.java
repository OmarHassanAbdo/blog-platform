package com.example.blog_platform.Controller;

import com.example.blog_platform.dtos.UserResponseDTO;
import com.example.blog_platform.entity.PasswordResetToken;
import com.example.blog_platform.entity.Role;
import com.example.blog_platform.entity.User;
import com.example.blog_platform.entity.enums.AppRole;
import com.example.blog_platform.repository.RoleRepository;
import com.example.blog_platform.repository.UserRepository;
import com.example.blog_platform.security.JWT.JwtUtils;
import com.example.blog_platform.security.requests.Login;
import com.example.blog_platform.security.requests.Signup;
import com.example.blog_platform.service.EmailService;
import com.example.blog_platform.service.interfaces.PasswordResetTokenService;
import com.example.blog_platform.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordResetTokenService passwordResetTokenService;
    @Autowired
    EmailService emailService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Signup request){

        User user = new User(request.getUserName() , request.getPassword() , request.getEmail());
        System.out.println(user);
        user = userService.createUser(user);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login request){
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername() , request.getPassword()));
        }
        catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateTokenFromUserName(request.getUsername());
        Map<String , Object> response = new HashMap<>();
        response.put("token" , jwt);
        response.put("userName" , request.getUsername());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("user email isn't found")
                );
        PasswordResetToken resetToken = passwordResetTokenService.createToken(user);
        String resetUrl = "http://localhost:8080/api/public/auth/reset-password?token=".concat(resetToken.getToken());
        emailService.sendEmail(user.getEmail(), "Password Reset Request",
                "Click the link to reset your password: " + resetUrl);
        return ResponseEntity.ok("password reset link sent to your email successfully...");
    }
    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token ,@RequestParam String  newPassword){
        if(!passwordResetTokenService.validateToken(token)){
            return ResponseEntity.status(401).body("your passed token isn't valid..");
        }
        passwordResetTokenService.resetPassword(token, newPassword);
        return ResponseEntity.ok("password reset successfully");
    }
}
