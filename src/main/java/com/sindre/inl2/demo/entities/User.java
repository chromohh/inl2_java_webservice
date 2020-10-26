package com.sindre.inl2.demo.entities;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
public class User {

    public User(@NotEmpty @UniqueElements @Size(min = 3, max = 50, message = "Username not valid, to short/long or empty") String username, @NotEmpty @Size(min = 1, max = 40, message = "Password to not valid, to long or empty") String password, ArrayList<Book> lendedBooks) {
        this.username = username;
        this.password = password;
        this.lendedBooks = lendedBooks;
    }

    public User() {
    }

    @Id
    private String userId;

    @NotEmpty
    @UniqueElements
    @Size(min = 3, max = 50, message = "Username not valid, to short/long or empty")
    private String username;

    @NotEmpty
    @Size(min = 1, max = 40, message = "Password to not valid, to long or empty")
    private String password;

    private Boolean isLibrarian;

    private ArrayList<Book> lendedBooks;
}
