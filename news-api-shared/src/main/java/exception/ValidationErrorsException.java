package exception;

import java.util.Map;

public class ValidationErrorsException extends GenericValidationException {
    private Map<String, String> errorMessages;
    public ValidationErrorsException(Map<String, String> errorMessages) {
        super("Error occurred during validation");
        this.errorMessages = errorMessages;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }
}
