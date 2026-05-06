package com.chirag.teamtaskmanager.controller;

import com.chirag.teamtaskmanager.dto.ResponseStructure;
import com.chirag.teamtaskmanager.entity.User;
import com.chirag.teamtaskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseStructure<User>> signup(@RequestBody User user) {
        ResponseStructure<User> response = userService.signup(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(@RequestBody Map<String, String> request) {
        ResponseStructure<String> response = userService.login(request.get("email"), request.get("password"));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
