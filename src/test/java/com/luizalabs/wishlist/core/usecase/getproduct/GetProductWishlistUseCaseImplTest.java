package com.luizalabs.wishlist.core.usecase.getproduct;

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
public class GetProductWishlistUseCaseImplTest {

    @InjectMocks
    private GetProductWishlistUseCaseImpl getProductWishlistUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    @Test
    public void shouldGetProductSuccessfully() {
        final var input = create(GetProductWishlistInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getId), input.wishlistId())
                .set(field(Wishlist::getCustomerId), input.customerId())
                .set(field(Wishlist::getProducts), Set.of(input.productId()))
                .create();

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.of(wishlist));

        var result = getProductWishlistUseCase.execute(input);

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        assertEquals(input.productId(), result.get());
    }

    @Test
    public void shouldThrowExceptionWhenWishlistNotFound() {
        final var input = create(GetProductWishlistInput.class);

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.empty());

        assertThrows(WishlistResourceNotFoundException.class, () -> getProductWishlistUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
    }

    @Test
    public void shouldReturnEmptyWhenProductNotFoundInWishlist() {
        final var input = create(GetProductWishlistInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getId), input.wishlistId())
                .set(field(Wishlist::getCustomerId), input.customerId())
                .set(field(Wishlist::getProducts), Set.of())
                .create();

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.of(wishlist));

        var result = getProductWishlistUseCase.execute(input);

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void shouldNotGetProductWhenThrowsException() {
        final var input = create(GetProductWishlistInput.class);

        doThrow(RuntimeException.class).when(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());

        assertThrows(RuntimeException.class, () -> getProductWishlistUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
    }
}
