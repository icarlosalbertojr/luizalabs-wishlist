package com.challenge.wishlist.core.repository;

import com.challenge.wishlist.core.domain.Wishlist;

import java.util.Optional;

public interface WishlistRepository {

    Wishlist save(Wishlist wishlist);

    Optional<Wishlist> findByIdAndCustomerId(String id, String customerId);

    Optional<Wishlist> findByCustomerId(String customerId);

}
