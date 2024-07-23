package com.luizalabs.wishlist.core.usecase.getallproducts;


import com.luizalabs.wishlist.core.repository.WishlistRepository;
import com.luizalabs.wishlist.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class GetAllProductsUseCase implements UseCase<GetAllProductsInput, Set<String>> {

    private final WishlistRepository wishlistRepository;

    @Override
    public Set<String> execute(GetAllProductsInput input) {
        final var wishlist = wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId())
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        log.info("[GetAllProductsUseCase] getting wishlist {} products", input.wishlistId());
        return wishlist.getProducts();
    }
}
