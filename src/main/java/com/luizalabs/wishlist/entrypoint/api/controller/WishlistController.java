package com.luizalabs.wishlist.entrypoint.api.controller;

import com.luizalabs.wishlist.core.domain.Wishlist;
import com.luizalabs.wishlist.entrypoint.api.payload.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

public interface WishlistController {

    @Operation(summary = "Create new Wishlist")
    @ApiResponse(responseCode = "201", description = "Wishlist created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ApiResponse(responseCode = "409", description = "Customer already has a wishlist", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ApiResponse(responseCode = "500", description = "Server error to create wishlist", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CreateWishlistPayload.Response create(@RequestBody @Valid CreateWishlistPayload.Request request);

    @Operation(summary = "Add product on wishlist")
    @ApiResponse(responseCode = "204", description = "Product added")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ApiResponse(responseCode = "500", description = "Server error to get product", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{wishlistId}/customer/{customerId}/product")
    void addProduct(
            @PathVariable String customerId,
            @PathVariable String wishlistId,
            @RequestBody @Valid AddWishlistProductPayload request);


    @Operation(summary = "Remove product from wishlist")
    @ApiResponse(responseCode = "204", description = "Product removed")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ApiResponse(responseCode = "500", description = "Server error to get product", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{wishlistId}/customer/{customerId}/product/{productId}")
    void removeProduct(
            @PathVariable String customerId,
            @PathVariable String wishlistId,
            @PathVariable String productId);


    @Operation(summary = "Get one single product from wishlist")
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ApiResponse(responseCode = "500", description = "Server error to get product", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @GetMapping("/{wishlistId}/customer/{customerId}/product/{productId}")
    WishlistOneProductPayload getProduct(
            @PathVariable String customerId,
            @PathVariable String wishlistId,
            @PathVariable String productId);


    @Operation(summary = "Get all products from wishlist")
    @ApiResponse(responseCode = "200", description = "Products found")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @ApiResponse(responseCode = "500", description = "Server error to create", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistErrorPayload.class))
    })
    @GetMapping("/{wishlistId}/customer/{customerId}/product")
    Set<String> getAllProduct(
            @PathVariable String customerId,
            @PathVariable String wishlistId);


}
