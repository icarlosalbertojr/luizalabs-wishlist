package com.challenge.wishlist.core.usecase.create;

import com.challenge.wishlist.core.domain.Wishlist;
import com.challenge.wishlist.core.exception.WishlistAlreadyCreatedException;
import com.challenge.wishlist.core.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CreateWishlistUseCaseImpl implements CreateWishlistUseCase {

    private final Integer maxLimit;
    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist execute(CreateWishlistInput input) {
        try {

            final var result = wishlistRepository.findByCustomerId(input.customerId());
            if (!result.isEmpty()) {
                throw new WishlistAlreadyCreatedException();
            }

            log.info("[CreateWishlistUseCase] creating wishlist for customer {}", input.customerId());
            final var newWishlist = new Wishlist(input.customerId(), maxLimit);

            var saved = wishlistRepository.save(newWishlist);
            log.info("[CreateWishlistUseCase] wishlist was created for customer {}", input.customerId());

            return saved;
        } catch (WishlistAlreadyCreatedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("error to process wishlist creation", e);
        }

    }

}
