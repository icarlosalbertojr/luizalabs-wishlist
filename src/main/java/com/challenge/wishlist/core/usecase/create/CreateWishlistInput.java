package com.challenge.wishlist.core.usecase.create;

import jakarta.validation.constraints.NotEmpty;

public record CreateWishlistInput(
       @NotEmpty String customerId
) {}
