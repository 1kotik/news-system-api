package by.kotik.newsservice.exception;

import exception.GenericNotFoundException;

import java.util.UUID;

public class NewsNotFoundException extends GenericNotFoundException {
    public NewsNotFoundException(UUID newsId) {
        super("News with ID " + newsId + " not found");
    }
}
