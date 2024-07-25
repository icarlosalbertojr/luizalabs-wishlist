package com.luizalabs.wishlist.config;

import com.luizalabs.wishlist.core.repository.WishlistRepository;
import com.luizalabs.wishlist.dataprovider.database.repository.MongoWishlistRepository;
import com.luizalabs.wishlist.dataprovider.database.repository.WishlistRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataProviderBeansConfig {

    @Bean
    public WishlistRepository wishlistRepository(final MongoWishlistRepository mongoWishlistRepository) {
        return new WishlistRepositoryImpl(mongoWishlistRepository);
    }

}
