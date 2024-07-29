package com.luizalabs.wishlist.entrypoint.api.handler;

import com.luizalabs.wishlist.core.exception.WishlistAlreadyCreatedException;
import com.luizalabs.wishlist.core.exception.WishlistReachOutLimit;
import com.luizalabs.wishlist.core.exception.WishlistResourceNotFoundException;
import com.luizalabs.wishlist.entrypoint.api.payload.WishlistErrorPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class WishlistExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WishlistErrorPayload> handle(MethodArgumentNotValidException e) {
        final var detail = e.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return new ResponseEntity<>(new WishlistErrorPayload(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WishlistReachOutLimit.class)
    public ResponseEntity<WishlistErrorPayload> handle(WishlistReachOutLimit e) {
        return new ResponseEntity<>(new WishlistErrorPayload(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WishlistResourceNotFoundException.class)
    public ResponseEntity<WishlistErrorPayload> handle(WishlistResourceNotFoundException e) {
        return new ResponseEntity<>(new WishlistErrorPayload(HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WishlistAlreadyCreatedException.class)
    public ResponseEntity<WishlistErrorPayload> handle(WishlistAlreadyCreatedException e) {
        return new ResponseEntity<>(new WishlistErrorPayload(HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage()),
                HttpStatus.CONFLICT);
    }

}
