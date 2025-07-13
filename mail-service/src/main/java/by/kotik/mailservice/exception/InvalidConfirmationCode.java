package by.kotik.mailservice.exception;

import exception.GenericValidationException;

public class InvalidConfirmationCode extends GenericValidationException {
    public InvalidConfirmationCode(String message) {
        super(message);
    }
}
