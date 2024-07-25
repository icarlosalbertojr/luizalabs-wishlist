package com.luizalabs.wishlist.core.usecase.removeproduct;

public record RemoveWishlistProductInput(
        String customerId,
        String wishlistId,
        String productId
) {}
