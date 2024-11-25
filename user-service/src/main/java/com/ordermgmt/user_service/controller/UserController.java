package com.ordermgmt.user_service.controller;

import com.ordermgmt.user_service.model.Privilege;
import com.ordermgmt.user_service.model.User;
import com.ordermgmt.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getUser(userId).get());
    }

    @GetMapping("/privileges/{userId}")
    public ResponseEntity<List<Privilege>> getUserPrivileges(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getUserPrivileges(userId));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
