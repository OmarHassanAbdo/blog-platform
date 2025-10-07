package com.example.blog_platform.Controller;

import com.example.blog_platform.dtos.UserRequestDTO;
import com.example.blog_platform.dtos.UserResponseDTO;
import com.example.blog_platform.entity.User;
import com.example.blog_platform.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>>getAllUsers(){
        List<User>users = userService.listAllUsers();
        List<UserResponseDTO>userResponseDTOS = userService.listAllUsers()
                .stream().map(
                        user -> new UserResponseDTO(user)
                ).toList();
        return ResponseEntity.ok(userResponseDTOS);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO>getUserById(
            @PathVariable("userId") Long userId
    ){
        UserResponseDTO userResponseDTO = new UserResponseDTO(userService.getUserById(userId));
        return ResponseEntity.ok(userResponseDTO);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO>updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserRequestDTO userRequestDTO
            ){
            User user = UserRequestDTO.toUser(userRequestDTO, userId);
            UserResponseDTO updatedUser = new UserResponseDTO(userService.updateUser(user , userId));
            return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?>deleteUser(
            @PathVariable("userId") Long userId
    ){
        userService.deleteUserById(userId);
        return ResponseEntity.ok("user deleted successfully");
    }
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<UserResponseDTO>>getUserFriends(
            @PathVariable("userId") Long userId
    ){
        List<UserResponseDTO>userFriends = userService.getUserById(userId).getFriends()
                .stream().map(
                        friend -> new UserResponseDTO(friend)
                ).toList();
        return ResponseEntity.ok(userFriends);
    }
    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?>addFriend(
            @PathVariable("userId") Long userId,
            @PathVariable("friendId") Long friendId
    ){
        userService.addFriend(userId,friendId);
        return ResponseEntity.ok("friend added successfully");
    }
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?>deleteFriend(
            @PathVariable("userId") Long userId,
            @PathVariable("friendId") Long friendId
    ){
        userService.removeFriend(userId,friendId);
        return ResponseEntity.ok("friend removed successfully");
    }
}
