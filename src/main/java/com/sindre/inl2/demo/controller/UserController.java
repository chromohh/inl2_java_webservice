package com.sindre.inl2.demo.controller;

import com.sindre.inl2.demo.entities.User;
import com.sindre.inl2.demo.services.BookService;
import com.sindre.inl2.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Rest API
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @PostMapping("/open")
    public ResponseEntity<User> save(@Validated @ModelAttribute User user){
        return ResponseEntity.ok(userService.save(user));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<User>> findAll(@RequestParam(required = false) String name){
        return ResponseEntity.ok(userService.findAll(name));
    }
}
