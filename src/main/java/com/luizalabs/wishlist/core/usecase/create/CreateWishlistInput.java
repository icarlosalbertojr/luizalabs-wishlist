package com.luizalabs.wishlist.core.usecase.create;

import jakarta.validation.constraints.NotEmpty;

public record CreateWishlistInput(
        @NotEmpty String customerId
) {}
