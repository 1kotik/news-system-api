package by.kotik.newsservice.exception;

import exception.GenericAuthorizationException;

import java.util.UUID;

public class UnauthorizedNewsModifyingException extends GenericAuthorizationException {
    public UnauthorizedNewsModifyingException(UUID newsId, UUID userId) {
        super(String.format("Modifying news with ID %s is forbidden for user %s", newsId, userId));
    }
}
