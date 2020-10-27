package com.sindre.inl2.demo.custom_validators;

import com.sindre.inl2.demo.entities.Book;
import com.sindre.inl2.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<Category, String> {

    @Autowired
    private BookService bookService;

    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {
        return bookService.isCategoryExisting(category);
    }
}
