package com.sindre.inl2.demo.repositories;

import com.sindre.inl2.demo.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends MongoRepository<Book, String> {
}
