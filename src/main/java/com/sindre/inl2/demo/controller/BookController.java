package com.sindre.inl2.demo.controller;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController // Rest API
@Slf4j
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/open")
    public ResponseEntity<List<Book>> findAllBooks(@RequestParam(required = false) String title, @RequestParam(required = false) Boolean sortByTitle){
        return ResponseEntity.ok(bookService.findAll(title, sortByTitle));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<Book> save(@Validated @ModelAttribute Book book){
        return ResponseEntity.ok(bookService.save(book));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @ModelAttribute @Valid Book book){
        bookService.update(id, book);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        bookService.delete(id);
    }

}
