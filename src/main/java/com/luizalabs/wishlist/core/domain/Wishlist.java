package com.luizalabs.wishlist.core.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Wishlist {

    private String id;
    private final String customerId;
    private Set<String> products;
    private final Integer maxLimit;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public Wishlist(final String customerId, final Integer maxLimit) {
        this.customerId = customerId;
        this.maxLimit = maxLimit;
        this.products = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }

    public void addProduct(final String productId) {
        if (products.size() == maxLimit) {
            throw new IllegalArgumentException("Wishlist reach out it limit");
        }
        products.add(productId);
        updated();
    }

    public boolean removeProduct(final String productId) {
        final var productWasRemoved = products.remove(productId);
        if (productWasRemoved) {
            updated();
        }
        return productWasRemoved;
    }

    public Optional<String> getProduct(final String productId) {
        return products.stream()
                .filter(it -> it.equals(productId))
                .findFirst();
    }

    private void updated() {
        updatedAt = LocalDateTime.now();
    }

}
