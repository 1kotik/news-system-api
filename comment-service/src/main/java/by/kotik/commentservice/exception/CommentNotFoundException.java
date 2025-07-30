package by.kotik.commentservice.exception;

import exception.GenericNotFoundException;

import java.util.UUID;

public class CommentNotFoundException extends GenericNotFoundException {
    public CommentNotFoundException(UUID commentId) {
        super("Comment with ID " + commentId + " not found");
    }
}
