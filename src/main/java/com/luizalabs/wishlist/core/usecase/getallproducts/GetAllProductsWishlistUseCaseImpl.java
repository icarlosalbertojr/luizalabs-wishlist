package com.luizalabs.wishlist.core.usecase.getallproducts;


import com.luizalabs.wishlist.core.exception.WishlistResourceNotFoundException;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class GetAllProductsWishlistUseCaseImpl implements GetAllProductsWishlistUseCase {

    private final WishlistRepository wishlistRepository;

    @Override
    public Set<String> execute(GetAllProductsWishlistInput input) {
        final var wishlist = wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId())
                .orElseThrow(() -> new WishlistResourceNotFoundException("wishlist not found"));
        log.info("[GetAllProductsUseCase] getting wishlist {} products", input.wishlistId());
        return wishlist.getProducts();
    }
}
