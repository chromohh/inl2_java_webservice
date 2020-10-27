package com.sindre.inl2.demo.services;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.repositories.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepository;

    //todo
    @GetMapping
    public List<Book> findAll(String title){
        log.info("Requesting all books, or matching search");
        var books = bookRepository.findAll();

        if(title!=null){
            books = books.stream()
                    .filter(b -> b.getTitle()
                            .toLowerCase()
                            .contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return books;
    }

    @PutMapping
    public Book save(Book book){
        log.info("Saving");
        return bookRepository.save(book);
    }

    @PutMapping
    public void update(String id, Book book){
        log.info("Updating book");
        if(!bookRepository.existsById(id)){
            log.error(String.format("Could not find user by id ", id));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find the user by id ", id));
        }

        book.setId(id);

        bookRepository.save(book);
    }



    @DeleteMapping
    public void delete(String id){
        if(!bookRepository.existsById(id)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Could not find user by id" + id));
        }
        bookRepository.deleteById(id);
    }

    public Boolean isCategoryExisting(String category){
        //Borde nog flytta listan till databasen.
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
