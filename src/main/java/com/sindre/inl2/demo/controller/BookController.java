package com.sindre.inl2.demo.controller;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Rest API
@Slf4j
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> findAllUsers(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping
    public Book save(@Validated @ModelAttribute Book book){
        return bookService.save(book);
    }

}
