package com.luizalabs.wishlist.core.usecase.create;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import com.luizalabs.wishlist.core.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CreateWishlistUseCase implements UseCase<CreateWishlistInput, Wishlist> {

    private final Integer maxLimit;
    private final WishlistRepository wishlistRepository;

    public Wishlist execute(CreateWishlistInput input) {
        log.info("[CreateWishlistUseCase] creating wishlist for customer {}", input.customerId());
        final var newWishlist = new Wishlist(input.customerId(), maxLimit);

        var saved = wishlistRepository.save(newWishlist);
        log.info("[CreateWishlistUseCase] wishlist was created for customer {}", input.customerId());

        return saved;
    }

}
