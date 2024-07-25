package com.luizalabs.wishlist.dataprovider.database.mapper;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.dataprovider.database.entity.WishlistEntity;

public class WishlistDatabaseMapper {

    public static WishlistEntity convert(Wishlist wishlist) {
        return WishlistEntity.builder()
                .id(wishlist.getId())
                .customerId(wishlist.getCustomerId())
                .products(wishlist.getProducts())
                .maxLimit(wishlist.getMaxLimit())
                .updatedAt(wishlist.getUpdatedAt())
                .createdAt(wishlist.getCreatedAt())
                .build();
    }

    public static Wishlist convert(WishlistEntity wishlist) {
        return Wishlist.builder()
                .id(wishlist.getId())
                .customerId(wishlist.getCustomerId())
                .products(wishlist.getProducts())
                .maxLimit(wishlist.getMaxLimit())
                .updatedAt(wishlist.getUpdatedAt())
                .createdAt(wishlist.getCreatedAt())
                .build();
    }

}
