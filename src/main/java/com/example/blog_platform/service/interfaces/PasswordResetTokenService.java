package com.example.blog_platform.service.interfaces;

import com.example.blog_platform.entity.PasswordResetToken;
import com.example.blog_platform.entity.User;

public interface PasswordResetTokenService {
    public PasswordResetToken createToken(User user);
    public boolean validateToken(String token);
    public void resetPassword(String token, String newPassword);
}
