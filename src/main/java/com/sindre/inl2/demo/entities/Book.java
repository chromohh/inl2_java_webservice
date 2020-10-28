package com.sindre.inl2.demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sindre.inl2.demo.custom_validators.Category;
import com.sindre.inl2.demo.custom_validators.CategoryValidator;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Constraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Book implements Serializable {
    private static final long serialVersionUID = 913715112220L;

    public Book(@NotEmpty @Size(min = 3, max = 50, message = "Title not valid, to short/long or empty") String title, @NotEmpty @Size(min = 2, max = 20, message = "Firstname length not valid, to short/long or empty") String authorFirstName, @NotEmpty @Size(min = 3, max = 20, message = "Lastname length not valid, to short/long or empty") String authorLastName, @Past(message = "Release date must be in past") LocalDate releaseDate, @NotEmpty @Size(min = 2, max = 20, message = "language length not valid, to short/long or empty") String language, String category) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.releaseDate = releaseDate;
        this.language = language;
        this.category = category;
        this.lendedUser = "none";
    }

    public Book() {
    }

    @Id
    private String id;

    @NotEmpty
    @Size(min = 3, max = 50, message = "Title not valid, to short/long or empty")
    private String title;

    @NotEmpty
    @Size(min = 2, max = 20, message = "Firstname length not valid, to short/long or empty")
    private String authorFirstName;

    @NotEmpty
    @Size(min = 3, max = 20, message = "Lastname length not valid, to short/long or empty")
    private String authorLastName;

    @Past(message = "Release date must be in past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate releaseDate;

    @NotEmpty
    @Size(min = 2, max = 20, message = "language length not valid, to short/long or empty")
    private String language;

    //Cool hemmasnickrad validerare
    @Category
    private String category;

    private String lendedUser;
}
