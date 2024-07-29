package com.challenge.wishlist.core.exception;

public class WishlistAlreadyCreatedException extends RuntimeException {

    public WishlistAlreadyCreatedException() {
        super("customer already has wishlist");
    }

}
