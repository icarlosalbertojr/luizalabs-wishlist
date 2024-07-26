package com.luizalabs.wishlist.core.usecase.addproduct;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.core.exception.WishlistResourceNotFoundException;
import com.luizalabs.wishlist.core.repository.WishlistRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
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
public class AddProductWishlistUseCaseImplTest {

    @InjectMocks
    private AddProductWishlistUseCaseImpl addProductWishlistUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    @Test
    public void shouldAddProductToWishlistSuccessfully() {
        final var input = create(AddProductWishlistInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getId), input.wishlistId())
                .set(field(Wishlist::getCustomerId), input.customerId())
                .create();

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        var result = addProductWishlistUseCase.execute(input);

        final var wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        verify(wishlistRepository).save(wishlistCaptor.capture());

        assertEquals(input.wishlistId(), result.getId());
        assertEquals(input.customerId(), result.getCustomerId());
        assertEquals(wishlist, wishlistCaptor.getValue());
    }

    @Test
    public void shouldThrowExceptionWhenWishlistNotFound() {
        final var input = create(AddProductWishlistInput.class);

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.empty());

        assertThrows(WishlistResourceNotFoundException.class, () -> addProductWishlistUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        verify(wishlistRepository, times(0)).save(any(Wishlist.class));
    }

    @Test
    public void shouldNotAddProductWhenSaveThrowsException() {
        final var input = create(AddProductWishlistInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getId), input.wishlistId())
                .set(field(Wishlist::getCustomerId), input.customerId())
                .create();

        when(wishlistRepository.findByIdAndCustomerId(input.wishlistId(), input.customerId()))
                .thenReturn(Optional.of(wishlist));
        doThrow(RuntimeException.class).when(wishlistRepository).save(any(Wishlist.class));

        assertThrows(RuntimeException.class, () -> addProductWishlistUseCase.execute(input));

        verify(wishlistRepository).findByIdAndCustomerId(input.wishlistId(), input.customerId());
        verify(wishlistRepository).save(any(Wishlist.class));
    }
}
