package com.example.blog_platform.service.interfaces;

import com.example.blog_platform.entity.User;

import java.util.List;

public interface UserService {
    public User createUser(User user);
    public User deleteUserById(Long id);
    public User updateUser(User user , Long id);
    public User getUserById(Long id);
    public List<User> addFriend(Long userId, Long friendId);
    public List<User> removeFriend(Long userId, Long friendId);
    public List<User> listAllUsers();
}
