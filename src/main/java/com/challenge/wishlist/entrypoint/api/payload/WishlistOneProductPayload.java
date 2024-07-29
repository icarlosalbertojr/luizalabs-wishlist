package com.challenge.wishlist.entrypoint.api.payload;

public record WishlistOneProductPayload(
        String customerId,
        String wishlistId,
        String productId
) {}
