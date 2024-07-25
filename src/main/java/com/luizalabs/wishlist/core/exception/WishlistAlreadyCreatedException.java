package com.luizalabs.wishlist.core.exception;

import org.springframework.http.HttpStatus;

public class WishlistAlreadyCreatedException extends RuntimeException {

    public WishlistAlreadyCreatedException() {
        super("customer already has wishlist");
    }

}
