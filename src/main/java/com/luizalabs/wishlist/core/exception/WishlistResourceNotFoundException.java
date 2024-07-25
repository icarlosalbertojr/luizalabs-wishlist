package com.luizalabs.wishlist.core.exception;

import org.springframework.http.HttpStatus;

public class WishlistResourceNotFoundException extends RuntimeException {

    public WishlistResourceNotFoundException(String message) {
        super(message);
    }
}
