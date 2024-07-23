package com.luizalabs.wishlist.core.domain;

import java.util.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Wishlist {

    private String id;
    private String customerId;
    private Set<String> products;
    private Integer productAmountLimit;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public Wishlist(String customerId, Integer productAmountLimit) {
        this.customerId = customerId;
        this.productAmountLimit = productAmountLimit;
        this.products = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }

    public void addProduct(@NotNull String productId) {
        if (products.size() == productAmountLimit) {
            throw new IllegalArgumentException("Wishlist reach out it limit");
        }
        products.add(productId);
        updated();
    }

    public boolean removeProduct(@NotNull String productId) {
        var productWasRemoved = products.remove(productId);
        if (productWasRemoved) {
            updated();
        }
        return productWasRemoved;
    }

    public Optional<String> getProduct(@NotNull String productId) {
        return products.stream()
                .filter(it -> it.equals(productId))
                .findFirst();
    }

    private void updated() {
        updatedAt = LocalDateTime.now();
    }

}
