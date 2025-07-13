package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ErrorResponse;
import exception.AppException;
import exception.GenericAuthorizationException;
import exception.GenericNotFoundException;
import exception.GenericValidationException;
import exception.ValidationErrorsException;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import util.ExceptionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;
    private final String SERVICE_NAME;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException e) {
        return ResponseEntity.status(e.getCode())
                .body(new ErrorResponse(e.getCode(), e.getError(), e.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
        ErrorResponse errorResponse = ExceptionUtils.mapToErrorResponse(e.contentUTF8(), objectMapper);
        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        return handleAppException(new GenericNotFoundException("Resource not found: " + e.getResourcePath()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return handleAppException(new GenericValidationException("Illegal data format"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return handleAppException(new GenericAuthorizationException("Access Denied."));
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = getErrorMessagesIfMethodArgumentNotValidException(e);
        return handleValidationErrorsException(new ValidationErrorsException(errors));
    }

    @ExceptionHandler(exception = HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        Map<String, String> errors = getErrorMessagesIfHandlerMethodValidationException(e);
        return handleValidationErrorsException(new ValidationErrorsException(errors));
    }

    @ExceptionHandler(exception = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = getErrorMessagesIfConstraintViolationException(e);
        return handleValidationErrorsException(new ValidationErrorsException(errors));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationErrorsException(ValidationErrorsException e) {
        return ResponseEntity.status(e.getCode())
                .body(new ErrorResponse(e.getCode(), e.getError(), e.getMessage(), e.getErrorMessages()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Throwable e) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, String.format("%s Error", SERVICE_NAME), e.getMessage()));
    }

    private Map<String, String> getErrorMessagesIfMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.put(field, message);
        });

        e.getBindingResult().getGlobalErrors().forEach(globalError -> {
            String object = globalError.getObjectName();
            String message = globalError.getDefaultMessage();
            errors.put(object, message);
        });
        return errors;
    }

    private Map<String, String> getErrorMessagesIfHandlerMethodValidationException(HandlerMethodValidationException e) {
        Map<String, String> errors = new HashMap<>();
        List<ParameterValidationResult> results = e.getParameterValidationResults();
        results.forEach(parameterValidationResult -> {
            String field = parameterValidationResult.getMethodParameter().getParameterName();
            String messages = parameterValidationResult.getResolvableErrors()
                    .stream()
                    .map(MessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining());
            errors.put(field, messages);
        });
        return errors;
    }

    private Map<String, String> getErrorMessagesIfConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String field = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.put(field, message);
        });
        return errors;
    }
}
