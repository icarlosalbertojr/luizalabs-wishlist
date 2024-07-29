package com.challenge.wishlist.core.usecase.getallproducts;

import jakarta.validation.constraints.NotEmpty;

public record GetAllProductsWishlistInput(
        @NotEmpty String customerId,
        @NotEmpty String wishlistId
) {
}
