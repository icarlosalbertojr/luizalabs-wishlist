package com.challenge.wishlist.core.usecase.create;

import com.challenge.wishlist.core.domain.Wishlist;
import com.challenge.wishlist.core.exception.WishlistAlreadyCreatedException;
import com.challenge.wishlist.core.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.instancio.Instancio.create;
import static org.instancio.Instancio.of;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateWishlistUseCaseImplTest {

    private CreateWishlistUseCaseImpl createWishlistUseCase;

    @Mock
    private WishlistRepository wishlistRepository;

    private final Integer maxLimit = 20;

    @BeforeEach
    public void before() {
        createWishlistUseCase = new CreateWishlistUseCaseImpl(maxLimit, wishlistRepository);
    }

    @Test
    public void shouldCreateWishlistSuccessfully() {
        final var input = create(CreateWishlistInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getCustomerId), input.customerId())
                .set(field(Wishlist::getMaxLimit), maxLimit)
                .create();

        when(wishlistRepository.findByCustomerId(input.customerId())).thenReturn(Optional.empty());
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        var result = createWishlistUseCase.execute(input);

        final var wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);

        verify(wishlistRepository).findByCustomerId(input.customerId());
        verify(wishlistRepository).save(wishlistCaptor.capture());

        assertEquals(input.customerId(), result.getCustomerId());
        assertEquals(maxLimit, wishlistCaptor.getValue().getMaxLimit());
    }

    @Test
    public void shouldNotCreateWhenCustomerAlreadyHasWishlist() {
        final var input = create(CreateWishlistInput.class);
        final var wishlist = of(Wishlist.class)
                .set(field(Wishlist::getCustomerId), input.customerId())
                .set(field(Wishlist::getMaxLimit), maxLimit)
                .create();

        when(wishlistRepository.findByCustomerId(input.customerId())).thenReturn(Optional.of(wishlist));

        assertThrows(WishlistAlreadyCreatedException.class, () -> createWishlistUseCase.execute(input));

        verify(wishlistRepository).findByCustomerId(input.customerId());
        verify(wishlistRepository, times(0)).save(any(Wishlist.class));
    }

    @Test
    public void shouldNotCreateWishlistWhenThrowsException() {
        final var input = create(CreateWishlistInput.class);

        when(wishlistRepository.findByCustomerId(input.customerId())).thenReturn(Optional.empty());
        doThrow(RuntimeException.class).when(wishlistRepository).save(any(Wishlist.class));

        assertThrows(RuntimeException.class, () -> createWishlistUseCase.execute(input));

        verify(wishlistRepository).findByCustomerId(input.customerId());
        verify(wishlistRepository).save(any(Wishlist.class));
    }



}