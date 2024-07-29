package com.challenge.wishlist.dataprovider.database.repository;

import com.challenge.wishlist.dataprovider.database.mapper.WishlistDatabaseMapper;
import com.challenge.wishlist.core.domain.Wishlist;
import com.challenge.wishlist.core.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {

    private final MongoWishlistRepository mongoWishlistRepository;

    @Override
    public Wishlist save(Wishlist wishlist) {
        final var saved = mongoWishlistRepository.save(WishlistDatabaseMapper.convert(wishlist));
        return WishlistDatabaseMapper.convert(saved);
    }

    @Override
    public Optional<Wishlist> findByIdAndCustomerId(String id, String customerId) {
        return mongoWishlistRepository.findByIdAndCustomerId(id, customerId)
                .map(WishlistDatabaseMapper::convert);
    }

    @Override
    public Optional<Wishlist> findByCustomerId(String customerId) {
        return mongoWishlistRepository.findByCustomerId(customerId)
                .map(WishlistDatabaseMapper::convert);
    }
}
