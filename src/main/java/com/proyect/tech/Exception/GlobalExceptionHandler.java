package com.proyect.tech.Exception;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(
	    MethodArgumentNotValidException ex,
	    WebRequest request
    ) {
	Map<String, String> fieldErrors = new LinkedHashMap<>();
	for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
	    fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
	}

	return buildResponse(
		HttpStatus.BAD_REQUEST,
		"Validation error",
		fieldErrors,
		request
	);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
	    ConstraintViolationException ex,
	    WebRequest request
    ) {
	Map<String, String> violations = new LinkedHashMap<>();
	ex.getConstraintViolations().forEach(v -> violations.put(v.getPropertyPath().toString(), v.getMessage()));

	return buildResponse(
		HttpStatus.BAD_REQUEST,
		"Constraint violation",
		violations,
		request
	);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(
	    HttpMessageNotReadableException ex,
	    WebRequest request
    ) {
	return buildResponse(
		HttpStatus.BAD_REQUEST,
		"Malformed JSON request",
		null,
		request
	);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, Object>> handleBusinessExceptions(
	    RuntimeException ex,
	    WebRequest request
    ) {
	return buildResponse(
		HttpStatus.BAD_REQUEST,
		ex.getMessage(),
		null,
		request
	);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
	    NoSuchElementException ex,
	    WebRequest request
    ) {
	return buildResponse(
		HttpStatus.NOT_FOUND,
		ex.getMessage(),
		null,
		request
	);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
	    DataIntegrityViolationException ex,
	    WebRequest request
    ) {
	return buildResponse(
		HttpStatus.CONFLICT,
		"Data integrity violation",
		null,
		request
	);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFound(
	    NoResourceFoundException ex,
	    WebRequest request
    ) {
	return buildResponse(
		HttpStatus.NOT_FOUND,
		"Resource not found",
		null,
		request
	);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
	    Exception ex,
	    WebRequest request
    ) {
	return buildResponse(
		HttpStatus.INTERNAL_SERVER_ERROR,
		"Unexpected internal error",
		null,
		request
	);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
	    HttpStatus status,
	    String message,
	    Object details,
	    WebRequest request
    ) {
	Map<String, Object> body = new LinkedHashMap<>();
	body.put("timestamp", LocalDateTime.now());
	body.put("status", status.value());
	body.put("error", status.getReasonPhrase());
	body.put("message", message);
	body.put("path", request.getDescription(false).replace("uri=", ""));

	if (details != null) {
	    body.put("details", details);
	}

	return ResponseEntity.status(status).body(body);
    }
}
