package com.example.blog_platform.service;

import com.example.blog_platform.entity.PasswordResetToken;
import com.example.blog_platform.entity.User;
import com.example.blog_platform.repository.PasswordResetTokenRepository;
import com.example.blog_platform.repository.UserRepository;
import com.example.blog_platform.service.interfaces.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Override
    public PasswordResetToken createToken(User user) {
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        return passwordResetTokenRepository.save(token);
    }

    @Override
    public boolean validateToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElse(null);
        return resetToken != null
                &&
                resetToken.getExpiryDate().isAfter(LocalDateTime.now())
                &&
                !resetToken.isUsed();
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(
                        () -> new RuntimeException("this token isn't in dataBase")
                );
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
