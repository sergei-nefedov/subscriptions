package pers.nefedov.subscriptions.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ResponseEntity<ErrorResponse> handleException(ConflictException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTraceId(MDC.get("traceId"));
        errorResponse.setMessage(exception.getMessage());
        log.error("Request failed: traceId={}", MDC.get("traceId"), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataRetrievalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ErrorResponse> handleException(DataRetrievalException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTraceId(MDC.get("traceId"));
        errorResponse.setMessage(exception.getMessage());
        log.error("Request failed: traceId={}", MDC.get("traceId"), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTraceId(MDC.get("traceId"));
        errorResponse.setMessage(exception.getMessage());
        log.error("Request failed: traceId={}", MDC.get("traceId"), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTraceId(MDC.get("traceId"));
        errorResponse.setMessage(exception.getMessage());
        log.error("Request failed: traceId={}", MDC.get("traceId"), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ResponseEntity<ErrorResponse> handleException(TypeMismatchException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTraceId(MDC.get("traceId"));
        errorResponse.setMessage(exception.getPropertyName() + " " + exception.getMessage());
        log.error("Request failed: traceId={}", MDC.get("traceId"), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ResponseEntity<ErrorResponse> handleException(IllegalStateException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTraceId(MDC.get("traceId"));
        errorResponse.setMessage(exception.getMessage());
        log.error("Request failed: traceId={}", MDC.get("traceId"), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private List<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream().map(error -> {
            ErrorResponse response = new ErrorResponse();
            response.setTraceId(MDC.get("traceId"));
            response.setMessage("field: " + error.getField() + " " + error.getDefaultMessage());
            return response;
        }).collect(Collectors.toList());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private List<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream().map(violation -> {
            ErrorResponse response = new ErrorResponse();
            response.setTraceId(MDC.get("traceId"));
            response.setMessage("field: " + violation.getPropertyPath() + ": " + violation.getMessage());
            return response;
        }).collect(Collectors.toList());
    }

    @ResponseBody
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @Schema(description = "Standard error response")
    public static class ErrorResponse {
        @Schema(description = "Trace ID for debugging", example = "d3b0a8c0-5e9a-4b7a-8c1a-9e3b0a8c0d2e")
        private String traceId;
        @Schema(description = "Error message", example = "User not found")
        private String message;
    }
}

