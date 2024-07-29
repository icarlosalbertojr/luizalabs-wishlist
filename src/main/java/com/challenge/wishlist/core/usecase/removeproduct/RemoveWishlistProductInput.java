package com.challenge.wishlist.core.usecase.removeproduct;

public record RemoveWishlistProductInput(
        String customerId,
        String wishlistId,
        String productId
) {}
