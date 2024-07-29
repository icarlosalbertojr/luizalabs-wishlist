package com.challenge.wishlist.dataprovider.database.repository;

import com.challenge.wishlist.dataprovider.database.entity.WishlistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoWishlistRepository extends MongoRepository<WishlistEntity, String> {

    Optional<WishlistEntity> findByIdAndCustomerId(String id, String customerId);

    Optional<WishlistEntity> findByCustomerId(String customerId);

}
