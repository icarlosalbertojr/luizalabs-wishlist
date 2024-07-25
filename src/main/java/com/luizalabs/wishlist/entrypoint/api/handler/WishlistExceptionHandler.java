package com.luizalabs.wishlist.entrypoint.api.handler;

import com.luizalabs.wishlist.core.exception.WishlistAlreadyCreatedException;
import com.luizalabs.wishlist.core.exception.WishlistResourceNotFoundException;
import com.luizalabs.wishlist.entrypoint.api.payload.WishlistErrorPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WishlistExceptionHandler {

    @ExceptionHandler(WishlistResourceNotFoundException.class)
    public ResponseEntity<WishlistErrorPayload> wishlistExceptionHandler(WishlistResourceNotFoundException e) {
        return new ResponseEntity<>(new WishlistErrorPayload(HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WishlistAlreadyCreatedException.class)
    public ResponseEntity<WishlistErrorPayload> wishlistExceptionHandler(WishlistAlreadyCreatedException e) {
        return new ResponseEntity<>(new WishlistErrorPayload(HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WishlistErrorPayload> exceptionHandler(Exception e) {
        return new ResponseEntity<>(new WishlistErrorPayload(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
