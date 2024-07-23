package com.luizalabs.wishlist.core.usecase.addproduct;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import com.luizalabs.wishlist.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AddProductWishlistUseCase implements UseCase<AddProductWishlistInput, Wishlist> {

    private final WishlistRepository wishlistRepository;

    public Wishlist execute(AddProductWishlistInput input) {
        final var wishlist = wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId())
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        log.info("[AddProductWishlistUseCase] adding product {} on wishlist {} for customer {}", input.productId(), input.wishlistId(), input.customerId());
        wishlist.addProduct(input.productId());
        final var saved = wishlistRepository.save(wishlist);

        log.info("[AddProductWishlistUseCase] product {} was added to wishlist {}", input.productId(), input.wishlistId());
        return saved;
    }

}
