package by.kotik.newsservice.exception;

import exception.GenericNotFoundException;

import java.util.UUID;

public class CategoryNotFoundException extends GenericNotFoundException {
    public CategoryNotFoundException(UUID categoryId) {
        super("Category with ID " + categoryId + " not found");
    }
}
