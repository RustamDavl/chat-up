package ru.rstd.chatupp.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.rstd.chatupp.exception.UserNotFoundException;
import ru.rstd.chatupp.exception.UsernameAlreadyExistsException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({UsernameAlreadyExistsException.class, UserNotFoundException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
