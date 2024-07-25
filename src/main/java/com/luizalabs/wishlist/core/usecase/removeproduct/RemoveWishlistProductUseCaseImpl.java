package com.luizalabs.wishlist.core.usecase.removeproduct;

import com.luizalabs.wishlist.core.exception.WishlistResourceNotFoundException;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RemoveWishlistProductUseCaseImpl implements RemoveWishlistProductUseCase {

    private final WishlistRepository wishlistRepository;

    @Override
    public Boolean execute(RemoveWishlistProductInput input) {
        final var wishlist = wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId())
                .orElseThrow(() -> new WishlistResourceNotFoundException("wishlist not found"));

        final var product = wishlist.getProduct(input.productId())
                .orElseThrow(() -> new WishlistResourceNotFoundException("product not found"));

        final var productWasRemoved = wishlist.removeProduct(product);

        wishlistRepository.save(wishlist);
        log.info(productWasRemoved ? "product {} was removed from wishlist {}" : "product {} wasn't removed from wishlist {}", input.productId(), input.wishlistId());

        return productWasRemoved;
    }

}
