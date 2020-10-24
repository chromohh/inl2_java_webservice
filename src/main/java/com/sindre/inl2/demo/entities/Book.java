package com.sindre.inl2.demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class Book implements Serializable {
    private static final long serialVersionUID = 913715112220L;

    @Id
    private String id;

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

    @NotEmpty
    @Size(min = 2, max = 20, message = "category length not valid, to short/long or empty")
    private String category;
}
