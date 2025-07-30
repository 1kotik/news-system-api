package by.kotik.commentservice.exception;

import exception.GenericAuthorizationException;

import java.util.UUID;

public class UnauthorizedCommentModifyingException extends GenericAuthorizationException {
    public UnauthorizedCommentModifyingException(UUID userId, UUID commentId) {
        super(String.format("Modifying comment with ID %s is forbidden for user %s", commentId, userId));
    }
}
