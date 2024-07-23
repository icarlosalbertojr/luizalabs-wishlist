package com.luizalabs.wishlist.core.usecase.create;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import com.luizalabs.wishlist.core.usecase.UseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class CreateWishlistUseCase implements UseCase<CreateWishlistInput, Wishlist> {

    private final Integer maxLimit;
    private final WishlistRepository wishlistRepository;

    @Autowired
    public CreateWishlistUseCase(
            @Value("${service.params.wishlist.maxLimit}") Integer maxLimit,
            WishlistRepository wishlistRepository) {
        this.maxLimit = maxLimit;
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist execute(CreateWishlistInput input) {
        log.info("[CreateWishlistUseCase] creating wishlist for customer {}", input.customerId());
        final var newWishlist = new Wishlist(input.customerId(), maxLimit);

        var saved = wishlistRepository.save(newWishlist);
        log.info("[CreateWishlistUseCase] wishlist was created for customer {}", input.customerId());

        return saved;
    }

}
