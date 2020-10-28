package com.sindre.inl2.demo.entities;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    public User(@NotEmpty @Size(min = 3, max = 50, message = "Username not valid, to short/long or empty") String username, @NotEmpty @Size(min = 1, max = 40, message = "Password to not valid, to long or empty") String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    @Id
    private String userId;

    @NotEmpty
    @Indexed(unique = true)
    @Size(min = 3, max = 50, message = "Username not valid, to short/long or empty")
    private String username;

    @NotEmpty
    @Size(min = 1, max = 40, message = "Password to not valid, to long or empty")
    private String password;

    private List<Book> lendedBooks = new ArrayList<Book>();

    private List<String> acl;
}
