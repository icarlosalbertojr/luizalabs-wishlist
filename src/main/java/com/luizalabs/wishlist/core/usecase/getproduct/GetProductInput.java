package com.luizalabs.wishlist.core.usecase.getproduct;

public record GetProductInput(
        String customerId,
        String wishlistId,
        String productId
) {
}
