package com.challenge.wishlist.entrypoint.api.payload;

import jakarta.validation.constraints.NotEmpty;

public record AddWishlistProductPayload(
        @NotEmpty String productId
) { }
