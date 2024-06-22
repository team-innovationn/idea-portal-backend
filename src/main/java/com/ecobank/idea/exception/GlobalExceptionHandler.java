package com.ecobank.idea.exception;

import com.ecobank.idea.dto.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.rmi.AccessException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle jakarta validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // MAp to hold validation errors
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(request.getDescription(false),       // API path
                HttpStatus.BAD_REQUEST,                                     // Status code
                validationErrors,                                     // Error message
                LocalDateTime.now()                                         // Time
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    // Handle already exists exception
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExistsException(UserAlreadyExistsException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(webRequest.getDescription(false),       // API path
                HttpStatus.BAD_REQUEST,                                     // Status code
                exception.getMessage(),                                     // Error message
                LocalDateTime.now()                                         // Time
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    // Authentication exception
    @ExceptionHandler({
            BadCredentialsException.class,
            UsernameNotFoundException.class,
            AccountExpiredException.class,
            LockedException.class,
            DisabledException.class,
            CredentialsExpiredException.class,
            ExpiredJwtException.class,
            AccessException.class,
            AccessDeniedException.class                 // Handle Method Level Exceptions
    })
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<ErrorResponseDTO> handleAuthException(RuntimeException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(webRequest.getDescription(false),       // API path
                UNAUTHORIZED,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, UNAUTHORIZED);
    }

    // Handle Duplication errors
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateRequest(DuplicateRequestException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(webRequest.getDescription(false),       // API path
                BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, BAD_REQUEST);
    }

    // Handle ResourceNotFound errors
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(webRequest.getDescription(false),       // API path
                BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, BAD_REQUEST);
    }

    // Runtime exceptions || Internal Server errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(RuntimeException exception, WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(webRequest.getDescription(false),       // API path
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
