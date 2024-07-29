package com.challenge.wishlist.core.usecase.getproduct;

public record GetProductWishlistInput(
        String customerId,
        String wishlistId,
        String productId
) {
}
