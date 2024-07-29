package com.luizalabs.wishlist.core.exception;

public class WishlistReachOutLimit extends RuntimeException {

    public WishlistReachOutLimit() {
        super("wishlist reach out it limit");
    }

}
