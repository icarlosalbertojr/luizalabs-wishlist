package com.luizalabs.wishlist.core.usecase.getallproducts;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.core.exception.WishlistResourceNotFoundException;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.instancio.Instancio.create;
import static org.instancio.Instancio.of;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllProductsWishlistUseCaseImplTest {

    @InjectMocks
    private GetAllProductsWishlistUseCaseImpl getAllProductsWishlistUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    @Test
    public void shouldGetAllProductsSuccessfully() {
        final var input = create(GetAllProductsWishlistInput.class);
        final var products = Set.of("product1", "product2", "product3");
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getId), input.wishlistId())
                .set(field(Wishlist::getCustomerId), input.customerId())
                .set(field(Wishlist::getProducts), products)
                .create();

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.of(wishlist));

        var result = getAllProductsWishlistUseCase.execute(input);

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        assertEquals(products, result);
    }

    @Test
    public void shouldThrowExceptionWhenWishlistNotFound() {
        final var input = create(GetAllProductsWishlistInput.class);

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.empty());

        assertThrows(WishlistResourceNotFoundException.class, () -> getAllProductsWishlistUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
    }

    @Test
    public void shouldNotGetAllProductsWhenThrowsException() {
        final var input = create(GetAllProductsWishlistInput.class);

        doThrow(RuntimeException.class).when(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());

        assertThrows(RuntimeException.class, () -> getAllProductsWishlistUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
    }
}
