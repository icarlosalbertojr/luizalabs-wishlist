package com.luizalabs.wishlist.entrypoint.api.payload;

public record WishlistOneProductPayload(
        String customerId,
        String wishlistId,
        String productId
) {}
