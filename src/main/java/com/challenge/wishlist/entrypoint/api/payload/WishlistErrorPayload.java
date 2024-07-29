package com.challenge.wishlist.entrypoint.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record WishlistErrorPayload(
        String status,
        Object detail
) {}
