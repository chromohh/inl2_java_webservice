package com.sindre.inl2.demo.controller;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController // Rest API
@Slf4j
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> findAllUsers(@RequestParam(required = false) String title){
        return ResponseEntity.ok(bookService.findAll(title));
    }

    @PostMapping("/admin")
    public Book save(@Validated @ModelAttribute Book book){
        return bookService.save(book);
    }

    @PutMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @ModelAttribute Book book){
        bookService.update(id, book);
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        bookService.delete(id);
    }

}
