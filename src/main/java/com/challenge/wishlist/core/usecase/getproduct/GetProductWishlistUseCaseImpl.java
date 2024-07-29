package com.challenge.wishlist.core.usecase.getproduct;

import com.challenge.wishlist.core.exception.WishlistResourceNotFoundException;
import com.challenge.wishlist.core.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class GetProductWishlistUseCaseImpl implements GetProductWishlistUseCase {

    private final WishlistRepository wishlistRepository;

    @Override
    public Optional<String> execute(GetProductWishlistInput input) {
        final var wishlist = wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId())
                .orElseThrow(() -> new WishlistResourceNotFoundException("wishlist not found"));
        log.info("[GetAllProductsUseCase] getting wishlist {} product {}", input.wishlistId(), input.productId());
        final var product = wishlist.getProduct(input.productId());
        return product;
    }
}
