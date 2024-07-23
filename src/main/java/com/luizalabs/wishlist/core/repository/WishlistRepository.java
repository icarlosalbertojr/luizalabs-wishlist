package com.luizalabs.wishlist.core.repository;

import com.luizalabs.wishlist.core.domain.Wishlist;

import java.util.Optional;

public interface WishlistRepository {

    Wishlist save(Wishlist wishlist);

    Optional<Wishlist> findByIdAndCustomerId(String id, String customerId);

}
