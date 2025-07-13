package by.kotik.userservice.exception;

import exception.GenericValidationException;

public class UsernameAlreadyExistsException extends GenericValidationException {
    public UsernameAlreadyExistsException(String username) {
        super("Username " + username + " already exists");
    }
}
