package com.challenge.wishlist.config;

import com.challenge.wishlist.core.repository.WishlistRepository;
import com.challenge.wishlist.dataprovider.database.repository.MongoWishlistRepository;
import com.challenge.wishlist.dataprovider.database.repository.WishlistRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataProviderBeansConfig {

    @Bean
    public WishlistRepository wishlistRepository(final MongoWishlistRepository mongoWishlistRepository) {
        return new WishlistRepositoryImpl(mongoWishlistRepository);
    }

}
