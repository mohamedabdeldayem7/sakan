package com.sakan.sakan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(Exception ex) {

//        RestError re = new RestError(HttpStatus.UNAUTHORIZED.toString(),
//                "Authentication failed at controller advice");
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "The email or password is incorrect!");

    }

    @ExceptionHandler(UserExistsException.class)
    public ProblemDetail handleAuthenticationException(UserExistsException ex) {

        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());

    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleAuthenticationException(RuntimeException ex) {

//        RestError re = new RestError(HttpStatus.UNAUTHORIZED.toString(),
//                "Authentication failed at controller advice");
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

    }
}

