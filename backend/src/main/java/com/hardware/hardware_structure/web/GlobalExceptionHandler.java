package com.hardware.hardware_structure.web;

import com.hardware.hardware_structure.core.error.ErrorDetails;
import com.hardware.hardware_structure.core.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildResponse("INVALID_ARGUMENT", ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDetails> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        return buildResponse("ILLEGAL_STATE", ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return buildResponse("RESOURCE_NOT_FOUND", ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JRException.class)
    public ResponseEntity<ErrorDetails> handleJRException(JRException ex, WebRequest request) {
        return buildResponse("REPORT_GENERATION_FAILED", ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Optional.ofNullable(error.getDefaultMessage()).orElse("Invalid value"),
                        (existing, replacement) -> existing
                ));

        ErrorDetails errorDetails = new ErrorDetails(
                "DTO_VALIDATION_FAILED",
                "Validation failed for one or more fields",
                request.getDescription(false),
                errors
        );

        log.warn("Dto validation failed: {} — {}", request.getDescription(false), errors);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        return buildResponse("INTERNAL_ERROR", ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDetails> buildResponse(String errorCode, Exception ex, WebRequest request, HttpStatus status) {
        ErrorDetails errorDetails = new ErrorDetails(
                errorCode,
                ex.getMessage(),
                request.getDescription(false)
        );

        log.error("Exception [{}] occurred: {} — {}", errorCode, ex.getMessage(), request.getDescription(false), ex);

        return new ResponseEntity<>(errorDetails, status);
    }
}
