package com.example.shop.config;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.ProblemDetail;
@RestControllerAdvice public class ApiErrorHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST) ProblemDetail validation(MethodArgumentNotValidException ex){ProblemDetail p=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Request validation failed");p.setTitle("Validation failed");p.setProperty("errors",ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).toList());return p;}
 @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class) ResponseEntity<ProblemDetail> status(org.springframework.web.server.ResponseStatusException ex){HttpStatusCode code=ex.getStatusCode();ProblemDetail p=ProblemDetail.forStatusAndDetail(code,ex.getReason()==null?"Request failed":ex.getReason());return ResponseEntity.status(code).body(p);}
}
