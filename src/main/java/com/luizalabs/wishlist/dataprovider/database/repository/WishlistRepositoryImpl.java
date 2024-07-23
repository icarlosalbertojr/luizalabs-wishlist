package com.luizalabs.wishlist.dataprovider.database.repository;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {

    private final MongoWishlistRepository mongoWishlistRepository;

    @Override
    public Wishlist save(Wishlist wishlist) {
        return null;
    }

    @Override
    public Optional<Wishlist> findByIdAndCustomerId(String id, String customerId) {
        return Optional.empty();
    }
}
