package com.chirag.teamtaskmanager.service;

import com.chirag.teamtaskmanager.dao.UserDao;
import com.chirag.teamtaskmanager.dto.ResponseStructure;
import com.chirag.teamtaskmanager.entity.User;
import com.chirag.teamtaskmanager.exception.UserNotFoundException;
import com.chirag.teamtaskmanager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseStructure<User> signup(User user) {
        if (userDao.existsByEmail(user.getEmail())) {
            ResponseStructure<User> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Email already registered");
            response.setData(null);
            return response;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userDao.saveUser(user);
        ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User registered successfully");
        response.setData(savedUser);
        return response;
    }

    public ResponseStructure<String> login(String email, String password) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            ResponseStructure<String> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid password");
            response.setData(null);
            return response;
        }

        String token = jwtUtil.generateToken(email);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Login successful");
        response.setData(token);
        return response;
    }

    public ResponseStructure<List<User>> getAllUsers() {
        List<User> users = userDao.findAllUsers();
        ResponseStructure<List<User>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users fetched successfully");
        response.setData(users);
        return response;
    }

    public ResponseStructure<User> getUserById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        ResponseStructure<User> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User fetched successfully");
        response.setData(user);
        return response;
    }
}