package com.challenge.wishlist.core.domain;

import com.challenge.wishlist.core.exception.WishlistReachOutLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistTest {

    private Wishlist wishlist;

    @BeforeEach
    public void setUp() {
        wishlist = new Wishlist("customer1", 5);
    }

    @Test
    public void shouldAddProductSuccessfully() {
        final String productId = "product1";

        wishlist.addProduct(productId);

        assertTrue(wishlist.getProduct(productId).isPresent());
        assertEquals(1, wishlist.getProducts().size());
        assertNotNull(wishlist.getUpdatedAt());
    }

    @Test
    public void shouldThrowExceptionWhenAddingProductExceedsLimit() {
        final String productId = "product1";
        for (int i = 0; i < 5; i++) {
            wishlist.addProduct("product" + i);
        }

        assertThrows(WishlistReachOutLimit.class, () -> wishlist.addProduct(productId));
    }

    @Test
    public void shouldRemoveProductSuccessfully() {
        final String productId = "product1";
        wishlist.addProduct(productId);

        boolean removed = wishlist.removeProduct(productId);

        assertTrue(removed);
        assertFalse(wishlist.getProduct(productId).isPresent());
        assertEquals(0, wishlist.getProducts().size());
        assertNotNull(wishlist.getUpdatedAt());
    }

    @Test
    public void shouldNotRemoveProductIfNotPresent() {
        final String productId = "product1";

        boolean removed = wishlist.removeProduct(productId);

        assertFalse(removed);
        assertEquals(0, wishlist.getProducts().size());
    }

    @Test
    public void shouldRetrieveProductSuccessfully() {
        final String productId = "product1";
        wishlist.addProduct(productId);

        Optional<String> retrievedProduct = wishlist.getProduct(productId);

        assertTrue(retrievedProduct.isPresent());
        assertEquals(productId, retrievedProduct.get());
    }

    @Test
    public void shouldNotRetrieveProductIfNotPresent() {
        final String productId = "product1";

        Optional<String> retrievedProduct = wishlist.getProduct(productId);

        assertFalse(retrievedProduct.isPresent());
    }

    @Test
    public void shouldUpdateTimestampsOnProductAdd() {
        final String productId = "product1";
        LocalDateTime beforeAdd = wishlist.getUpdatedAt();

        wishlist.addProduct(productId);

        LocalDateTime afterAdd = wishlist.getUpdatedAt();
        assertTrue(afterAdd.isAfter(beforeAdd));
    }

    @Test
    public void shouldUpdateTimestampsOnProductRemove() {
        final String productId = "product1";
        wishlist.addProduct(productId);
        LocalDateTime beforeRemove = wishlist.getUpdatedAt();

        wishlist.removeProduct(productId);

        LocalDateTime afterRemove = wishlist.getUpdatedAt();
        assertTrue(afterRemove.isAfter(beforeRemove));
    }
}
