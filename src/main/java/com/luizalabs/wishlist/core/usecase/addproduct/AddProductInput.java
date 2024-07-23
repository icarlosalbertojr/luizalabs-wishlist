package com.luizalabs.wishlist.core.usecase.addproduct;

import jakarta.validation.constraints.NotEmpty;

public record AddProductInput(
        @NotEmpty String wishlistId,
        @NotEmpty String customerId,
        @NotEmpty String productId
) {}
