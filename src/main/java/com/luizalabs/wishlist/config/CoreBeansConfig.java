package com.luizalabs.wishlist.config;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import com.luizalabs.wishlist.core.usecase.UseCase;
import com.luizalabs.wishlist.core.usecase.addproduct.AddProductWishlistInput;
import com.luizalabs.wishlist.core.usecase.addproduct.AddProductWishlistUseCase;
import com.luizalabs.wishlist.core.usecase.create.CreateWishlistInput;
import com.luizalabs.wishlist.core.usecase.create.CreateWishlistUseCase;
import com.luizalabs.wishlist.core.usecase.getallproducts.GetAllProductsWishlistInput;
import com.luizalabs.wishlist.core.usecase.getallproducts.GetAllProductsWishlistUseCase;
import com.luizalabs.wishlist.core.usecase.getproduct.GetProductWishlistInput;
import com.luizalabs.wishlist.core.usecase.getproduct.GetProductWishlistUseCase;
import com.luizalabs.wishlist.dataprovider.database.repository.MongoWishlistRepository;
import com.luizalabs.wishlist.dataprovider.database.repository.WishlistRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.Set;

@Configuration
public class CoreBeansConfig {

    private final Integer maxLimit;

    @Autowired
    public CoreBeansConfig(@Value("${service.params.wishlist.maxLimit}") Integer maxLimit) {
        this.maxLimit = maxLimit;
    }

    @Bean
    public WishlistRepository wishlistRepository(final MongoWishlistRepository mongoWishlistRepository) {
        return new WishlistRepositoryImpl(mongoWishlistRepository);
    }

    @Bean
    public UseCase<AddProductWishlistInput, Wishlist> addProductWishlistUseCase(final WishlistRepository repository) {
        return new AddProductWishlistUseCase(repository);
    }

    @Bean
    public UseCase<CreateWishlistInput, Wishlist> createWishlistUseCase(final WishlistRepository repository) {
        return new CreateWishlistUseCase(maxLimit, repository);
    }

    @Bean
    public UseCase<GetAllProductsWishlistInput, Set<String>> getAllProductsWishlistUseCase(final WishlistRepository repository) {
        return new GetAllProductsWishlistUseCase(repository);
    }

    @Bean
    public UseCase<GetProductWishlistInput, Optional<String>> getProductWishlistUseCase(final WishlistRepository repository) {
        return new GetProductWishlistUseCase(repository);
    }

}
