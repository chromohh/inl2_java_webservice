package com.sindre.inl2.demo.services;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.entities.User;
import com.sindre.inl2.demo.repositories.BookRepo;
import com.sindre.inl2.demo.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final BookRepo bookRepository;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> findAll(String user){
        log.info("Requesting all users, or matching search");
        var users = userRepository.findAll();

        if(user!=null){
            users = users.stream()
                    .filter(b -> b.getUsername()
                            .toLowerCase()
                            .contains(user.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return users;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, // 404 -> Not found
                String.format("Could not find the user by username %s.", username)));
    }

    @PutMapping
    public User save(User user){
        if(user == null){
            log.error(String.format("Empty body"));
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                    String.format("Empty body"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAcl(Arrays.asList("USER"));
        return userRepository.save(user);
    }
}
