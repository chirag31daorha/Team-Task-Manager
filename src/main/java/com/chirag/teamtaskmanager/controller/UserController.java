package com.chirag.teamtaskmanager.controller;

import com.chirag.teamtaskmanager.dto.ResponseStructure;
import com.chirag.teamtaskmanager.entity.User;
import com.chirag.teamtaskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        ResponseStructure<List<User>> response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<User>> getUserById(@PathVariable Long id) {
        ResponseStructure<User> response = userService.getUserById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
