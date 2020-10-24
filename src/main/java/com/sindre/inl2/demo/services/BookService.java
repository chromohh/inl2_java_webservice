package com.sindre.inl2.demo.services;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.repositories.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepository;

    @GetMapping
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @PutMapping
    public Book save(Book book){
        return bookRepository.save(book);
    }

    public Boolean isCategoryExisting(String category){
        //Borde nog flytta listan.
        List<String> categories = Arrays.asList("",
                "Fantasy",
                "Politics",
                "History",
                "Philosophy",
                "Programming",
                "Psychology",
                "Cooking");

        return (category != null && categories.stream().anyMatch(str -> str.matches(category)));
    }
}
