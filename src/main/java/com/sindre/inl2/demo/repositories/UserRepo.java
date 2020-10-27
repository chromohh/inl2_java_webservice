package com.sindre.inl2.demo.repositories;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
