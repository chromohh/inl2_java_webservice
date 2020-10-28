package com.sindre.inl2.demo.services;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.entities.User;
import com.sindre.inl2.demo.repositories.BookRepo;
import com.sindre.inl2.demo.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Empty body"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAcl(Arrays.asList("USER"));
        return userRepository.save(user);
    }

    @PutMapping
    public Book lendBook(String bookId){
        if(bookId == null){
            log.error(String.format("No book id"));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("No book id"));
        }
        if(bookRepository.findById(bookId).isEmpty()){
            log.error(String.format("Could not find book by id ", bookId));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find the book by id ", bookId));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentPrincipalName = authentication.getName();

        if(userRepository.findByUsername(currentPrincipalName).isEmpty()){
            log.error(String.format("Could not find user", currentPrincipalName));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find the user ", currentPrincipalName));
        }

        User user = userRepository.findByUsername(currentPrincipalName).get();
        Book book = bookRepository.findById(bookId).get();

        if(user.getLendedBooks().contains(book) || book.getLendedUser().equals(null) || book.getLendedUser().equals("None")){
            log.error(String.format("Book already lended", book.getTitle()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Book already lended ", book.getTitle()));
        }


        book.setLendedUser(currentPrincipalName);
        bookRepository.save(book);

        List<Book> lendedBook = user.getLendedBooks();
        lendedBook.add(book);
        user.setLendedBooks(lendedBook);
        userRepository.save(user);

        return book;
    }

    @PutMapping
    public Book returnBook(String bookId){
        if(bookId == null){
            log.error(String.format("No book id"));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("No book id"));
        }
        if(bookRepository.findById(bookId).isEmpty()){
            log.error(String.format("Could not find book by id ", bookId));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find the book by id ", bookId));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentPrincipalName = authentication.getName();

        if(userRepository.findByUsername(currentPrincipalName).isEmpty()){
            log.error(String.format("Could not find user : ", currentPrincipalName));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find the user : ", currentPrincipalName));
        }

        User user = userRepository.findByUsername(currentPrincipalName).get();
        Book book = bookRepository.findById(bookId).get();

        if(userRepository.findByUsername(currentPrincipalName).get().getLendedBooks().stream().noneMatch(b -> b.getId().equals(bookId))){
            log.error(String.format("User has not lendend book", book.getTitle()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User has not lendend book: ", book.getTitle()));
        }


        List<Book> updatedBook = user.getLendedBooks();
        updatedBook.remove(book);
        user.setLendedBooks(updatedBook);
        userRepository.save(user);

        book.setLendedUser("none");
        bookRepository.save(book);

        return book;
    }
}
