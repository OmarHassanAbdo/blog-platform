package com.example.blog_platform.service;

import com.example.blog_platform.entity.Role;
import com.example.blog_platform.entity.User;
import com.example.blog_platform.entity.enums.AppRole;
import com.example.blog_platform.repository.RoleRepository;
import com.example.blog_platform.repository.UserRepository;
import com.example.blog_platform.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        if (userRepository.existsByUserName(user.getUserName()) || userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User's email or username already in use");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Default ROLE_USER not found in database"));

            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User deleteUserById(Long id) {
        User user = findUser(id);
        userRepository.deleteById(id);
        return user;
    }

    @Override
    public User updateUser(User user, Long id) {
        User oldUser = findUser(id);

        if (!Objects.equals(user.getId(), oldUser.getId())) {
            throw new RuntimeException("Wrong update data: mismatched IDs");
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return findUser(id);
    }

    @Override
    @Transactional
    public List<User> addFriend(Long userId, Long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);

        List<User> userFriends = user.getFriends();
        List<User> friendFriends = friend.getFriends();

        if (!userFriends.contains(friend)) {
            userFriends.add(friend);
        }
        if (!friendFriends.contains(user)) {
            friendFriends.add(user);
        }

        user.setFriends(userFriends);
        friend.setFriends(friendFriends);

        userRepository.save(user);
        userRepository.save(friend);

        return user.getFriends();
    }

    @Override
    @Transactional
    public List<User> removeFriend(Long userId, Long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);

        List<User> userFriends = user.getFriends();
        List<User> friendFriends = friend.getFriends();

        if (userFriends.contains(friend)) {
            userFriends.remove(friend);
        }
        if (friendFriends.contains(user)) {
            friendFriends.remove(user);
        }

        user.setFriends(userFriends);
        friend.setFriends(friendFriends);

        userRepository.save(user);
        userRepository.save(friend);

        return user.getFriends();
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found with id: " + id)
        );
    }
}
