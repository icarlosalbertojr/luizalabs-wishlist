package com.challenge.wishlist.core.usecase.removeproduct;

import com.challenge.wishlist.core.domain.Wishlist;
import com.challenge.wishlist.core.exception.WishlistResourceNotFoundException;
import com.challenge.wishlist.core.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.instancio.Instancio.create;
import static org.instancio.Instancio.of;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemoveWishlistProductUseCaseImplTest {

    @InjectMocks
    private RemoveWishlistProductUseCaseImpl removeWishlistProductUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    @Test
    public void shouldRemoveProductFromWishlistSuccessfully() {
        final var input = create(RemoveWishlistProductInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getId), input.wishlistId())
                .set(field(Wishlist::getCustomerId), input.customerId())
                .create();

        wishlist.addProduct(input.productId());

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        var result = removeWishlistProductUseCase.execute(input);

        final var wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        verify(wishlistRepository).save(wishlistCaptor.capture());

        assertEquals(wishlist, wishlistCaptor.getValue());
        assertEquals(wishlist, result);
    }

    @Test
    public void shouldThrowExceptionWhenWishlistNotFound() {
        final var input = create(RemoveWishlistProductInput.class);

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.empty());

        assertThrows(WishlistResourceNotFoundException.class, () -> removeWishlistProductUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        verify(wishlistRepository, times(0)).save(any(Wishlist.class));
    }

    @Test
    public void shouldThrowExceptionWhenProductNotFoundInWishlist() {
        final var input = create(RemoveWishlistProductInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getId), input.wishlistId())
                .set(field(Wishlist::getCustomerId), input.customerId())
                .create();

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.of(wishlist));

        assertThrows(WishlistResourceNotFoundException.class, () -> removeWishlistProductUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        verify(wishlistRepository, times(0)).save(any(Wishlist.class));
    }

    @Test
    public void shouldNotRemoveProductWhenSaveThrowsException() {
        final var input = create(RemoveWishlistProductInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getId), input.wishlistId())
                .set(field(Wishlist::getCustomerId), input.customerId())
                .create();

        wishlist.addProduct(input.productId());

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.of(wishlist));
        doThrow(RuntimeException.class).when(wishlistRepository).save(any(Wishlist.class));

        assertThrows(RuntimeException.class, () -> removeWishlistProductUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        verify(wishlistRepository).save(any(Wishlist.class));
    }
}
