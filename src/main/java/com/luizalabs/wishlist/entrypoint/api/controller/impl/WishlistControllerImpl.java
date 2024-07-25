package com.luizalabs.wishlist.entrypoint.api.controller.impl;

import com.luizalabs.wishlist.core.exception.WishlistResourceNotFoundException;
import com.luizalabs.wishlist.core.usecase.addproduct.AddProductWishlistInput;
import com.luizalabs.wishlist.core.usecase.addproduct.AddProductWishlistUseCase;
import com.luizalabs.wishlist.core.usecase.create.CreateWishlistInput;
import com.luizalabs.wishlist.core.usecase.create.CreateWishlistUseCase;
import com.luizalabs.wishlist.core.usecase.getallproducts.GetAllProductsWishlistInput;
import com.luizalabs.wishlist.core.usecase.getallproducts.GetAllProductsWishlistUseCase;
import com.luizalabs.wishlist.core.usecase.getproduct.GetProductWishlistInput;
import com.luizalabs.wishlist.core.usecase.getproduct.GetProductWishlistUseCase;
import com.luizalabs.wishlist.entrypoint.api.controller.WishlistController;
import com.luizalabs.wishlist.entrypoint.api.payload.AddWishlistProductPayload;
import com.luizalabs.wishlist.entrypoint.api.payload.CreateWishlistPayload;
import com.luizalabs.wishlist.entrypoint.api.payload.WishlistOneProductPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistControllerImpl implements WishlistController {

    private final CreateWishlistUseCase createWishlistUseCase;
    private final GetAllProductsWishlistUseCase getAllProductsWishlistUseCase;
    private final GetProductWishlistUseCase getProductWishlistUseCase;
    private final AddProductWishlistUseCase addProductWishlistUseCase;

    @Override
    public CreateWishlistPayload.Response create(CreateWishlistPayload.Request request) {
        final var created = createWishlistUseCase.execute(new CreateWishlistInput(request.consumerId()));
        return new CreateWishlistPayload.Response(created.getId(), created.getCustomerId(), created.getMaxLimit(), created.getCreatedAt());
    }

    @Override
    public void addProduct(String customerId, String wishlistId, AddWishlistProductPayload request) {
        addProductWishlistUseCase.execute(new AddProductWishlistInput(wishlistId, customerId, request.productId()));
    }

    @Override
    public void removeProduct(String customerId, String wishlistId, String productId) {

    }

    @Override
    public WishlistOneProductPayload getProduct(String customerId, String wishlistId, String productId) {
        final var product = getProductWishlistUseCase.execute(new GetProductWishlistInput(customerId, wishlistId, productId));
        if (product.isEmpty()) {
            throw new WishlistResourceNotFoundException("product not found");
        }
        return new WishlistOneProductPayload(customerId, wishlistId, product.get());
    }

    @Override
    public Set<String> getAllProduct(String customerId, String wishlistId) {
        return getAllProductsWishlistUseCase.execute(new GetAllProductsWishlistInput(
                customerId,
                wishlistId
        ));
    }
}
