package com.luizalabs.wishlist.core.usecase.getproduct;

import com.luizalabs.wishlist.core.repository.WishlistRepository;
import com.luizalabs.wishlist.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class GetProductUseCase implements UseCase<GetProductInput, Optional<String>> {

    private final WishlistRepository wishlistRepository;

    @Override
    public Optional<String> execute(GetProductInput input) {
        final var wishlist = wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId())
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        log.info("[GetAllProductsUseCase] getting wishlist {} product {}", input.wishlistId(), input.productId());
        final var product = wishlist.getProduct(input.productId());
        return product;
    }
}
