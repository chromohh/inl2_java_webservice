package com.sindre.inl2.demo.services;

import com.sindre.inl2.demo.entities.User;
import com.sindre.inl2.demo.repositories.BookRepo;
import com.sindre.inl2.demo.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final BookRepo bookRepository;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, // 404 -> Not found
                String.format("Could not find the user by username %s.", username)));
    }
}
