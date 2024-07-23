package com.luizalabs.wishlist.core.usecase.getallproducts;

import jakarta.validation.constraints.NotEmpty;

public record GetAllProductsInput(
        @NotEmpty String customerId,
        @NotEmpty String wishlistId
) {
}
